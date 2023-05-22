package dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.data;

import dev.imlukas.bedwarsinventoryswapper.utils.schedulerutil.ScheduledTask;
import lombok.Getter;

public class ScheduleThread implements ScheduleBuilderBase {

    @Getter
    private final ScheduleData data;

    public ScheduleThread(ScheduleData data) {
        this.data = data;
    }

    public ScheduledTask sync() {
        data.setSync(true);
        return new ScheduledTask(data.getPlugin(), data);
    }

    public ScheduledTask async() {
        data.setSync(false);
        return new ScheduledTask(data.getPlugin(), data);
    }


}
