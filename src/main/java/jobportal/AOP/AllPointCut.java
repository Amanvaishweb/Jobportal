package jobportal.AOP;


import org.aspectj.lang.annotation.Pointcut;

public class AllPointCut {
    @Pointcut("execution(* jobportal.Service.JobService..*(..))")
    public void jobService(){

    }
    @Pointcut("execution(* jobportal.Service.JobService.get*(..))")
    public void Allgetter(){

    }
}
