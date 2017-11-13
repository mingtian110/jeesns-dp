package com.luntan.deppon.listener;

import com.luntan.deppon.core.utils.Const;
import com.luntan.deppon.core.utils.JeesnsConfig;
import com.luntan.deppon.core.utils.SpringContextHolder;
import com.luntan.deppon.model.system.Config;
import com.luntan.deppon.service.system.IConfigService;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.List;

/**
 * Created by cubc-luntan 2017/5/25.
 */
public class InitListener implements ServletContextListener {

    public InitListener() {
    }


    public void contextInitialized(ServletContextEvent sce) {
        try {
            Const.PROJECT_PATH = sce.getServletContext().getContextPath();
            sce.getServletContext().setAttribute("basePath", Const.PROJECT_PATH);
            sce.getServletContext().setAttribute("uploadCubcPath", "/requestImage?path=/opt/cubcfile/jeesns");//自定义上传路径
            sce.getServletContext().setAttribute("uploadCubcPathSub", "/opt/cubcfile/jeesns");//自定义上传路径
            JeesnsConfig jeesnsConfig = SpringContextHolder.getBean("jeesnsConfig");
            sce.getServletContext().setAttribute("jeesnsConfig",jeesnsConfig);
            String frontTemplate = jeesnsConfig.getFrontTemplate();
            sce.getServletContext().setAttribute("frontTemplate",frontTemplate);
            String managePath = Const.PROJECT_PATH + "/" + jeesnsConfig.getManagePath();
            sce.getServletContext().setAttribute("managePath",managePath);
            IConfigService configService = SpringContextHolder.getBean("configService");
            List<Config> configList = configService.allList();
            for (Config config : configList) {
                sce.getServletContext().setAttribute(config.getJkey().toUpperCase(),config.getJvalue());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }

    public void contextDestroyed(ServletContextEvent sce) {

    }
}
