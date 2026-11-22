# AI usage: CI pipeline (SIS 3)

## Tool and model

- **Tool:** Claude (claude.ai, agent mode with access to a cloud workspace and a clone of this repository)
- **Model:** Claude Opus 5.5

## Prompts

_P.S. I don't use AI with long academically structured prompts, sorry in advance._

1. [SIS 2: CI pipeline for Playwright, step-by-step guide PDF] Look, we gotta do this sis 3, my repo is here https://github.com/verdenhaus/Software-Testing-SIS2" I want you to guide me step by step through the whole thing for java so that by the end what we got will satisfy all the requirements and I will have an Idea of what I'm doing
2. I ran the test after changing pom right, for some reason the buyproduct test is getting timeout even tho it was working properly yesterday and all the other days
3. [error stacktrace]
4. yes I did the step 2 to change into env vars and it worked when I checked with customer2, ughhh they got to customer3 too, it was locked bcs of wrong password attempts. Alright I'll do all the steps above and check in
5. [attached first failed Add Ci pipeline ss] what do I do here? create a pull request? this is right after pushing from git bash
6. [attached error ss] oh lord (ubuntu fix here)
7. [error stacktrace + ss] wow everything failed, they all have pretty much the same error, that pasted stack is from shard 1/4 (finding out cloudflare blocking here and shards not working proeprly)
8. [error stacktrace] There's the docker logs, I pushed the print which one down thing, the workflow's in progress (found out image being build for different processor type so wrapped in QEMU)
9. [error stacktrace] okay so at least some progress, now here what went red How did buy product fail if u said images give like fresh record
10. wait before anything check https://github.com/verdenhaus/Software-Testing-SIS2/tree/ci-pipeline I have long gone changed the credentials to customer2 after u said it the first time, also reset password doesn't actually reset the password, it just gives a confirm alert or wrong, it's a forgot password flow (found out about the password actually being changed here, comment about it apparently exists only in the source code)

## What the AI did

Claude gave me a step by step guide into completing the SIS 3 on java set up, essentially translating the typescript guide into java. It helped me debug the errors and write the README.md file including the changes during the CI set up.

## What I did myself

Full implementation of the steps, creating files, editing them, working with git, working through errors with AI's help, measurements, screenshots for README.md

## Review

I have learned to set up a CI pipeline in this SIS, as a result, everything works now. I have faced several challenges including version missmatch, bot protection from site, processor type missmatch and apparent password change causing errors, but everything was resolved with appropriate fixes.
