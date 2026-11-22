package ci;

import org.junit.platform.engine.FilterResult;
import org.junit.platform.engine.TestDescriptor;
import org.junit.platform.launcher.PostDiscoveryFilter;

public class ShardFilter implements PostDiscoveryFilter {

    @Override
    public FilterResult apply(TestDescriptor descriptor) {
        String spec = System.getProperty("shard", System.getenv("SHARD"));
        if (spec == null || spec.isBlank() || !descriptor.isTest()) {
            return FilterResult.included("no sharding / container");
        }

        String[] parts = spec.trim().split("/");
        int index = Integer.parseInt(parts[0].trim());
        int total = Integer.parseInt(parts[1].trim());
        if (total < 1 || index < 1 || index > total) {
            throw new IllegalArgumentException("Invalid shard '" + spec + "', expected i/n");
        }

        int bucket = Math.floorMod(descriptor.getUniqueId().toString().hashCode(), total);
        boolean mine = bucket == index - 1;
        return FilterResult.includedIf(mine, () -> "in shard " + spec, () -> "belongs to another shard");
    }
}