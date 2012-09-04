package com.feature.resources.server.config.qutarz;

import com.feature.resources.server.config.qutarz.jobs.AutoAuditGraphicJob;
import org.nnsoft.guice.guartz.QuartzModule;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;

/**
 * User: ZouYanjian
 * Date: 8/31/12
 * Time: 1:24 PM
 * FileName:AppQutarzModule
 */
public class AppQuartzModule extends QuartzModule{
    @Override
    protected void schedule() {
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity("AutoAudit_every_15_minutes")
                .withSchedule(SimpleScheduleBuilder.repeatMinutelyForever(15)).startNow().build();
        scheduleJob(AutoAuditGraphicJob.class).withTrigger(trigger);
    }
}
