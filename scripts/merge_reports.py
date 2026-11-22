#!/usr/bin/env python3
"""Merge the JUnit XML files of all CI shards into one JUnit file and one HTML report.

Playwright for Java has no blob reporter and no `merge-reports` command, so this script does
that job for the Java/Maven setup.

Usage: merge_reports.py <shard-results-dir> <merged-junit.xml> <html-report-dir>

<shard-results-dir> holds one folder per shard (test-results-1 ... test-results-4), each with
the surefire XML files under reports/ and the failure traces under test-results/.
"""
import html
import shutil
import sys
import xml.etree.ElementTree as ET
from pathlib import Path


def status_of(case):
    if case.find("failure") is not None or case.find("error") is not None:
        return "failed"
    if case.find("skipped") is not None:
        return "skipped"
    if case.find("flakyFailure") is not None or case.find("flakyError") is not None:
        return "flaky"
    return "passed"


def message_of(case):
    for tag in ("failure", "error", "flakyFailure", "flakyError"):
        el = case.find(tag)
        if el is not None:
            text = el.get("message") or ""
            stack = el.findtext("stackTrace") or el.text or ""
            return (text + "\n" + stack).strip()
    return ""


def main(src, merged_xml, html_dir):
    src, merged_xml, html_dir = Path(src), Path(merged_xml), Path(html_dir)
    cases = []
    for xml_file in sorted(src.glob("*/reports/TEST-*.xml")):
        shard = xml_file.parts[len(src.parts)]  # e.g. test-results-2
        for case in ET.parse(xml_file).getroot().iter("testcase"):
            cases.append((shard, case))

    # --- merged JUnit ---------------------------------------------------------------
    by_class = {}
    for shard, case in cases:
        by_class.setdefault(case.get("classname", "unknown"), []).append(case)

    root = ET.Element("testsuites", name="Playwright Tests")
    totals = {"tests": 0, "failures": 0, "errors": 0, "skipped": 0, "time": 0.0}
    for classname, items in sorted(by_class.items()):
        suite = ET.SubElement(root, "testsuite", name=classname)
        counts = {"tests": len(items), "failures": 0, "errors": 0, "skipped": 0}
        time = 0.0
        for case in items:
            counts["failures"] += case.find("failure") is not None
            counts["errors"] += case.find("error") is not None
            counts["skipped"] += case.find("skipped") is not None
            time += float(case.get("time") or 0)
            suite.append(case)
        for key, value in counts.items():
            suite.set(key, str(value))
            totals[key] += value
        suite.set("time", f"{time:.3f}")
        totals["time"] += time
    for key, value in totals.items():
        root.set(key, f"{value:.3f}" if key == "time" else str(value))

    merged_xml.parent.mkdir(parents=True, exist_ok=True)
    ET.indent(root)
    ET.ElementTree(root).write(merged_xml, encoding="utf-8", xml_declaration=True)

    # --- HTML report ----------------------------------------------------------------
    html_dir.mkdir(parents=True, exist_ok=True)
    traces_dir = html_dir / "data"
    for shard_dir in sorted(p for p in src.iterdir() if p.is_dir()):
        results = shard_dir / "test-results"
        if results.is_dir():
            shutil.copytree(results, traces_dir / shard_dir.name, dirs_exist_ok=True)

    summary = {s: 0 for s in ("passed", "flaky", "failed", "skipped")}
    rows = []
    for shard, case in sorted(cases, key=lambda c: (c[1].get("classname", ""), c[1].get("name", ""))):
        status = status_of(case)
        summary[status] += 1
        cls = case.get("classname", "")
        name = case.get("name", "")
        simple = cls.rsplit(".", 1)[-1]
        method = name.split("(")[0]
        attachments = sorted((traces_dir / shard / f"{simple}.{method}").glob("*")) if traces_dir.exists() else []
        links = " ".join(
            f'<a href="{html.escape(str(a.relative_to(html_dir)))}">{html.escape(a.name)}</a>' for a in attachments
        )
        msg = message_of(case)
        details = f"<details><summary>details</summary><pre>{html.escape(msg)}</pre></details>" if msg else ""
        rows.append(
            f'<tr class="{status}"><td><span class="badge {status}">{status}</span></td>'
            f"<td>{html.escape(simple)}</td><td>{html.escape(name)}{details}</td>"
            f"<td>{html.escape(shard.replace('test-results-', ''))}</td>"
            f'<td class="num">{float(case.get("time") or 0):.1f}s</td><td>{links}</td></tr>'
        )

    total = sum(summary.values())
    page = f"""<!DOCTYPE html>
<html lang="en"><head><meta charset="utf-8"><title>Playwright Tests report</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<style>
 body{{font-family:system-ui,-apple-system,Segoe UI,Roboto,sans-serif;margin:2rem;color:#1f2328}}
 h1{{font-size:1.4rem;margin:0 0 .5rem}} .muted{{color:#656d76}}
 .cards{{display:flex;gap:.75rem;margin:1rem 0 1.5rem;flex-wrap:wrap}}
 .card{{border:1px solid #d0d7de;border-radius:8px;padding:.6rem 1rem;min-width:7rem}}
 .card b{{display:block;font-size:1.4rem}}
 table{{border-collapse:collapse;width:100%}} th,td{{text-align:left;padding:.45rem .6rem;border-bottom:1px solid #eaeef2;vertical-align:top}}
 th{{font-size:.8rem;text-transform:uppercase;color:#656d76}} .num{{text-align:right;font-variant-numeric:tabular-nums}}
 .badge{{border-radius:999px;padding:.1rem .55rem;font-size:.8rem;font-weight:600}}
 .badge.passed{{background:#dafbe1;color:#1a7f37}} .badge.failed{{background:#ffebe9;color:#cf222e}}
 .badge.flaky{{background:#fff8c5;color:#9a6700}} .badge.skipped{{background:#eaeef2;color:#57606a}}
 pre{{white-space:pre-wrap;font-size:.8rem;background:#f6f8fa;padding:.6rem;border-radius:6px;max-height:20rem;overflow:auto}}
</style></head><body>
<h1>Playwright Tests</h1>
<div class="muted">Merged from {len({s for s, _ in cases})} shard(s). Traces (.zip) open with
<code>mvn exec:java -e -Dexec.mainClass=com.microsoft.playwright.CLI -Dexec.args="show-trace trace.zip"</code>
or by dropping them on <a href="https://trace.playwright.dev">trace.playwright.dev</a>.</div>
<div class="cards">
 <div class="card"><b>{total}</b>total</div>
 <div class="card"><b>{summary['passed']}</b>passed</div>
 <div class="card"><b>{summary['flaky']}</b>flaky</div>
 <div class="card"><b>{summary['failed']}</b>failed</div>
 <div class="card"><b>{summary['skipped']}</b>skipped</div>
</div>
<table><thead><tr><th>Status</th><th>Class</th><th>Test</th><th>Shard</th><th class="num">Time</th><th>Attachments</th></tr></thead>
<tbody>
{chr(10).join(rows) if rows else '<tr><td colspan="6">No test results found.</td></tr>'}
</tbody></table>
</body></html>
"""
    (html_dir / "index.html").write_text(page, encoding="utf-8")
    print(f"Merged {total} tests from {len(cases)} results: {summary}")
    print(f"JUnit: {merged_xml}\nHTML:  {html_dir / 'index.html'}")


if __name__ == "__main__":
    if len(sys.argv) != 4:
        sys.exit(__doc__)
    main(*sys.argv[1:])
