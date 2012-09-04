package com.feature.resources.server.config.qutarz.jobs;

import com.feature.resources.server.dao.GraphicDao;
import com.feature.resources.server.domain.Graphic;
import com.feature.resources.server.dto.CheckResult;
import com.feature.resources.server.dto.CheckStatusDesc;
import com.feature.resources.server.dto.GraphicCheckDTO;
import com.feature.resources.server.service.CXServerService;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.util.Date;
import java.util.List;

/**
 * User: ZouYanjian
 * Date: 8/31/12
 * Time: 1:26 PM
 * FileName:AutoAuditGraphicJob
 */
public class AutoAuditGraphicJob implements Job{
    private static final Logger LOGGER = LoggerFactory.getLogger(AutoAuditGraphicJob.class);
    @Inject
    private GraphicDao graphicDao;
    private String cxServerUser;

    private Long timeSpan;

    @Inject
    private CXServerService cxServerService;
    @Inject
    public AutoAuditGraphicJob(@Named("cxServerUser")String cxServerUser,@Named("time.span")Long timeSpan){
        this.cxServerUser = cxServerUser;
        this.timeSpan =timeSpan;
    }
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<Graphic> graphicList = graphicDao.findByPageAndQueryTypeAndUserId(0, 1000, CheckStatusDesc.UNCHECKED, this.cxServerUser);

        if(graphicList == null && graphicList.size() == 0){
            return;
        }
        final Long now = System.currentTimeMillis();
        List<Graphic> filteredGraphics = Lists.newArrayList(Iterables.filter(graphicList, new Predicate<Graphic>() {
            @Override
            public boolean apply(@Nullable Graphic input) {
                Date createdDate = input.getCreateDate();
                if((now -createdDate.getTime())>900000){
                    return true;
                }
                return false;
            }
        }));
        List<String> ids = Lists.transform(filteredGraphics,new Function<Graphic, String>() {
            @Override
            public String apply(@Nullable Graphic input) {
                return input.getId().toString();
            }
        });
        if(ids != null && ids.size()>0){
            int row = graphicDao.updateCheckStatus(ids, CheckStatusDesc.CHECKED, CheckResult.PASS);
            LOGGER.info("updated rows:{}",row);
            GraphicCheckDTO graphicCheckDTO = new GraphicCheckDTO();
            graphicCheckDTO.setCheckResult(CheckResult.PASS.toString());
            graphicCheckDTO.setGraphicIds(ids);
            cxServerService.updateGraphicResourceAuditStatus(graphicCheckDTO);
            LOGGER.info(filteredGraphics.toString());
        }

    }
}
