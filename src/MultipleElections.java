package urna;

import java.util.Map;
import java.util.HashMap;

public class MultipleElections{
    private Map<String,AbstractElection> elections = new HashMap<String,AbstractElection>();
    
    // #if SegundoTurno
//@    protected Map<String,Boolean> secondRounds = new HashMap<String,Boolean>();
    // #endif
    
    private boolean status;

    private static MultipleElections instance;

    public static MultipleElections getInstance(){
        if(instance==null) instance = new MultipleElections();
        return instance;
    }

    public void addElection(String role,AbstractElection e){
        elections.put(role,e);
    }

    public AbstractElection get(String role){
        return elections.get(role);
    }

    public boolean getStatus(){
        return status;
    }

    public void start(String password) {
        for(Map.Entry<String, AbstractElection> entry : elections.entrySet()){
            entry.getValue().start(password);
        }
        this.status = true;
    }
    
    //ends election unless the conditions for second round are met
    public void finish(String password) {
    	Boolean ended;
    	this.status = false;
        for(Map.Entry<String, AbstractElection> entry : elections.entrySet()){
            ended = entry.getValue().finish(password);
            // #if SegundoTurno
//@            secondRounds.put(entry.getKey(), !ended);
//@            if(ended == false)
//@            	this.status = true;
            // #endif
        }
    }

    public String getResults(String password){
        String result="";
        for(Map.Entry<String, AbstractElection> entry : elections.entrySet()){
            result = result += entry.getValue().getResults(password);
        }
        return result;
    }
}
