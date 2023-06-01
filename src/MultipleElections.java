package urna;

import java.util.Map;
import java.util.HashMap;

class MultipleElections{
    private Map<String,AbstractElection> elections = new HashMap<String,AbstractElection>();
    
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
    
    public void finish(String password) {
        for(Map.Entry<String, AbstractElection> entry : elections.entrySet()){
            entry.getValue().finish(password);
        }
        this.status = false;
    }

    public String getResults(String password){
        String result="";
        for(Map.Entry<String, AbstractElection> entry : elections.entrySet()){
            result = result += entry.getValue().getResults(password);
        }
        return result;
    }
}