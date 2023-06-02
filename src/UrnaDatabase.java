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
          new Voter.Builder().electoralCard(voterData[0]).name(voterData[1]).state(voterData[2]).district(voterData[3]).nationality(voterData[4]).build());
      }
      myReader.close();
    } catch (Exception e) {
      System.out.println("Erro na inicializa��o dos dados");
      exit(1);
    }
    return voterMap;
  }
  
  protected static void loadCandidates(MultipleElections currentElection, String electionPassword) {
	  
	// #if Federal
    President presidentCandidate1 = new President.Builder().name("O Inomin�vel").number(123).party("Joalheiros").build();
    currentElection.get("President").addCandidate((Candidate) presidentCandidate1, electionPassword);
	  
    President presidentCandidate2 = new President.Builder().name("Maria").number(124).party("ED").build();
    currentElection.get("President").addCandidate((Candidate) presidentCandidate2, electionPassword);
	  
    FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder().name("Chupetinha").number(12345).party("PL").state("MG").build();
    currentElection.get("FederalDeputy").addCandidate((Candidate) federalDeputyCandidate1, electionPassword);
    FederalDeputy federalDeputyCandidate2 = new FederalDeputy.Builder().name("Cleber").number(54321).party("PDS2").state("MG").build();
    currentElection.get("FederalDeputy").addCandidate((Candidate)federalDeputyCandidate2, electionPassword);
    FederalDeputy federalDeputyCandidate3 = new FederalDeputy.Builder().name("Sofia").number(11211).party("IHC").state("MG").build();
    currentElection.get("FederalDeputy").addCandidate((Candidate)federalDeputyCandidate3, electionPassword);

    StateDeputy stateDeputyCandidate1 = new StateDeputy.Builder().name("Cleitudo_Estadual").number(64).party("Ot�rios").state("MG").build();
    currentElection.get("StateDeputy").addCandidate((Candidate)stateDeputyCandidate1, electionPassword);
    StateDeputy stateDeputyCandidate2 = new StateDeputy.Builder().name("Sofia_Estadual").number(32).party("Bobos").state("MG").build();
    currentElection.get("StateDeputy").addCandidate((Candidate)stateDeputyCandidate2, electionPassword);

    Senator senatorCandidate1 = new Senator.Builder().name("Cleitinho").number(44).party("Fechados com o Capiroto").state("MG").build();
    currentElection.get("Senator").addCandidate((Candidate) senatorCandidate1, electionPassword);
    Senator senatorCandidate2 = new Senator.Builder().name("Malika").number(54).party("PSOL").state("MG").build();
    currentElection.get("Senator").addCandidate((Candidate) senatorCandidate2, electionPassword);

    Governor governorCandidate1 = new Governor.Builder().name("Zema").number(15).party("Destruidores da Serra").state("MG").build();
    currentElection.get("Governor").addCandidate((Candidate)governorCandidate1, electionPassword);
    Governor governorCandidate2 = new Governor.Builder().name("Anestesia").number(30).party("Amigo do P�").state("MG").build();
    currentElection.get("Governor").addCandidate((Candidate)governorCandidate2, electionPassword);

    // #endif
    
    // #if Municipal
//@    Mayor mayorCandidate1 = new Mayor.Builder().name("Kalil").number(13).party("Mafiosos").district("Belo Horizonte").build();
//@    currentElection.get("Prefeito").addCandidate((Candidate)mayorCandidate1, electionPassword);
//@    Mayor mayorCandidate2 = new Mayor.Builder().name("Fuad").number(25).party("Kibes").district("Belo Horizonte").build();
//@    currentElection.get("Prefeito").addCandidate((Candidate)mayorCandidate2, electionPassword);
//@    
//@    
//@    CityCouncilor cityCouncilorCandidate1 = new CityCouncilor.Builder().name("Machadinho").number(725).party("Crime").district("Belo Horizonte").build();
//@    currentElection.get("Vereador").addCandidate((Candidate)cityCouncilorCandidate1, electionPassword);
//@    CityCouncilor cityCouncilorCandidate2 = new CityCouncilor.Builder().name("Paul�o").number(840).party("Trambic�o").district("Belo Horizonte").build();
//@    currentElection.get("Vereador").addCandidate((Candidate)cityCouncilorCandidate2, electionPassword);
//@    CityCouncilor cityCouncilorCandidate3 = new CityCouncilor.Builder().name("Henricudo").number(950).party("Quadrilha do Crime").district("Belo Horizonte").build();
//@    currentElection.get("Vereador").addCandidate((Candidate)cityCouncilorCandidate3, electionPassword);
//@    CityCouncilor cityCouncilorCandidate4 = new CityCouncilor.Builder().name("Marcelo M").number(666).party("Quadrilha do Crime").district("Belo Horizonte").build();
//@    currentElection.get("Vereador").addCandidate((Candidate)cityCouncilorCandidate4, electionPassword);
//@    CityCouncilor cityCouncilorCandidate5 = new CityCouncilor.Builder().name("Matheus Prado").number(667).party("Quadrilha da For�a Bruta").district("Belo Horizonte").build();
//@    currentElection.get("Vereador").addCandidate((Candidate)cityCouncilorCandidate5, electionPassword);
//@    
    // #endif

    // #if RealityShow
//@    RealityCandidate realityCandidate1 = new RealityCandidate.Builder().name("Babu").number(555).party("BBB").nationality("Brasil").build();
//@    currentElection.get("Participante Reality").addCandidate((Candidate)realityCandidate1, electionPassword);
    // #endif

  }


}
