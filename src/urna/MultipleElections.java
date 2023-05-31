package urna;

import java.util.Map;
import java.util.HashMap;

class MultipleElections{
    private Map<String,PoliticalElection> elections = new HashMap<String,PoliticalElection>();
    
    private boolean status;

    private static MultipleElections instance;

    public static MultipleElections getInstance(){
        if(instance==null) instance = new MultipleElections();
        return instance;
    }

    public void addElection(String role,PoliticalElection e){
        elections.put(role,e);
    }

    public PoliticalElection get(String role){
        return elections.get(role);
    }

    public boolean getStatus(){
        return status;
    }

    public void start(String password) {
        for(Map.Entry<String, PoliticalElection> entry : elections.entrySet()){
            entry.getValue().start(password);
        }
        this.status = true;
    }
    
    public void finish(String password) {
        for(Map.Entry<String, PoliticalElection> entry : elections.entrySet()){
            entry.getValue().finish(password);
        }
        this.status = false;
    }

    public String getResults(String password){
        String result="";
        for(Map.Entry<String, PoliticalElection> entry : elections.entrySet()){
            result = result += entry.getValue().getResults(password);
        }
        return result;
    }
}