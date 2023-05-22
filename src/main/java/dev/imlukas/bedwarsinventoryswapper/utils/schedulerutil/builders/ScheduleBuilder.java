package dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.builders;

import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleBuilderBase;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleData;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleThread;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleTimestamp;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Setter(AccessLevel.PACKAGE)
public class ScheduleBuilder implements ScheduleBuilderBase {

    @Getter
    private ScheduleData data;

    public ScheduleBuilder(JavaPlugin plugin) {
        this.data = new ScheduleData();
        this.data.setPlugin(plugin);
    }

    public static ScheduleThread runIn1Tick(JavaPlugin plugin, Runnable runnable) {
        return new ScheduleBuilder(plugin)
                .in(1)
                .ticks()
                .run(runnable);
    }

    public ScheduleTimestamp<RepeatableT2> every(long number) {
        data.setRepeating(true);
        return new ScheduleTimestamp<>(new RepeatableT2(data), number, data::setTicks);
    }

    public ScheduleTimestamp<ScheduleBuilderT2> in(long number) {
        data.setRepeating(false);
        return new ScheduleTimestamp<>(new ScheduleBuilderT2(data), number, data::setTicks);
    }
}
