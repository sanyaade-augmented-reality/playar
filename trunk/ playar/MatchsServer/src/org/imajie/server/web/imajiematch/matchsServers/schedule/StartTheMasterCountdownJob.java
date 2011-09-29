

package org.imajie.server.web.imajiematch.matchsServers.schedule;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imajie.server.web.imajiematch.matchsServers.openwig.Engine;
import org.imajie.server.web.imajiematch.matchsServers.sockets.PlayerServer;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;



public class StartTheMasterCountdownJob implements Job {

    //private static Log _log = LogFactory.getLog(StartTheMasterCountdownJob.class);

    /**
     * Empty constructor for job initilization
     */
    public StartTheMasterCountdownJob() {
    }

    public void execute(JobExecutionContext context)
        throws JobExecutionException {
        String jobID = context.getJobDetail().getName();
  
//        PlayerServer.currentThread().destroy();
//        Engine.requestSync();
//        //Engine.ui.end();
//        Engine.kill();
        //TODO IMPLEMENT A BETTER WAY TO EXIT THE PROCESS PlayerServer
        
        //System.out.println("Player Server SYSTEM STOPPED");
        
        //_log.info("jobID is: " + jobID + " executing at " + new Date());
    }

}










