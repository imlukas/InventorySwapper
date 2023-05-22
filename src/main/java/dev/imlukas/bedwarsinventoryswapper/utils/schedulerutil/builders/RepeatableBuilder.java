package dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.builders;

import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleBuilderBase;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleData;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleThread;
import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data.ScheduleTimestamp;
import lombok.Getter;

public class RepeatableBuilder extends ScheduleThread implements ScheduleBuilderBase {

    @Getter
    private final ScheduleData data;


    RepeatableBuilder(ScheduleData data) {
        super(data);
        this.data = data;
    }

    public ScheduleTimestamp<ScheduleThread> during(long amount) {
        return new ScheduleTimestamp<>(new ScheduleThread(data), amount, data::setCancelIn);
    }
}
