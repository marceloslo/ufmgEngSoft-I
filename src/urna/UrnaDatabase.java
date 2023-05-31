package urna;

import static java.lang.System.exit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UrnaDatabase {

  protected static Map<String, TSEProfessional> loadTSEProfessionals() {
    Map<String, TSEProfessional> TSEMap = new HashMap<>();
    TSEMap.put("cert", new CertifiedProfessional.Builder()
            .user("cert")
            .password("54321")
            .build());
        
    TSEMap.put("a", new CertifiedProfessional.Builder()
                .user("a")
                .password("1")
                .build());
        
    TSEMap.put("emp", new TSEEmployee.Builder()
            .user("emp")
            .password("12345")
            .build());
	return TSEMap;
  }
  
  protected static Map<String, Voter> loadVoters(String fileName){
    Map<String, Voter> voterMap = new HashMap<>();
    try {
      File myObj = new File(fileName);
      Scanner myReader = new Scanner(myObj);
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        var voterData = data.split(",");
        voterMap.put(voterData[0],
          new Voter.Builder().electoralCard(voterData[0]).name(voterData[1]).state(voterData[2]).build());
      }
      myReader.close();
    } catch (Exception e) {
      System.out.println("Erro na inicialização dos dados");
      exit(1);
    }
    return voterMap;
  }
  
  protected static void loadCandidates(MultipleElections currentElection, String electionPassword) {
    President presidentCandidate1 = new President.Builder().name("João").number(123).party("PDS1").build();
    currentElection.get("President").addCandidate((Candidate) presidentCandidate1, electionPassword);
	  
    President presidentCandidate2 = new President.Builder().name("Maria").number(124).party("ED").build();
    currentElection.get("President").addCandidate((Candidate) presidentCandidate2, electionPassword);
	  
    FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder().name("Carlos").number(12345).party("PDS1").state("MG").build();
    currentElection.get("FederalDeputy").addCandidate((Candidate) federalDeputyCandidate1, electionPassword);
	  
    FederalDeputy federalDeputyCandidate2 = new FederalDeputy.Builder().name("Cleber").number(54321).party("PDS2").state("MG").build();
    currentElection.get("FederalDeputy").addCandidate((Candidate)federalDeputyCandidate2, electionPassword);
	  
    FederalDeputy federalDeputyCandidate3 = new FederalDeputy.Builder().name("Sofia").number(11211).party("IHC").state("MG").build();
    currentElection.get("FederalDeputy").addCandidate((Candidate)federalDeputyCandidate3, electionPassword);
    
  }


}