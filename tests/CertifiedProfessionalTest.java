import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import urna.CertifiedProfessional;
import urna.MultipleElections;
import urna.PoliticalElection;
import urna.President;
import urna.FederalDeputy;
import urna.Voter;

public class CertifiedProfessionalTest {
  CertifiedProfessional certifiedProfessional;
  String user;
  String password;
  MultipleElections election;
  String electionPassword;
	  
  @BeforeEach
  public void setUp() {
	user = "Alberto";
	password="123";
	certifiedProfessional = new CertifiedProfessional.Builder()
			.user(user)
			.password(password)
			.build();
	electionPassword="123";
	//MultipleElections.reset();
	election = MultipleElections.getInstance();
	election.addElection("Presidente", new PoliticalElection(electionPassword));
	election.addElection("Deputado Federal", new PoliticalElection(electionPassword));
  }
  
  @Test
  public void testCertifiedProfessionalBuilder(){
    assertTrue("O nome do empregado do tse nao esta como definido pelo builder.", certifiedProfessional.getUser().equals(user));
	}

  @Test
  public void testStartSession() {
    certifiedProfessional.startSession(election, electionPassword);

    assertTrue("A eleicao nao foi iniciada como estperado.", election.getStatus() == true);
  }

  @Test
  public void testStartSessionWrong() {
    Throwable exception = assertThrows(Throwable.class, () -> {
      certifiedProfessional.startSession(election, "12");
    });

    String expectedMessage = "Senha inválida";
    String actualMessage = exception.getMessage();

    assertTrue("A exception de senha incorreta não foi lançada como esperado.", expectedMessage.equals(actualMessage));
  }

  @Test
  public void testEndSession() {

    certifiedProfessional.startSession(election, electionPassword);
    certifiedProfessional.endSession(election, electionPassword);

    assertTrue("A eleicao nao foi finalizadada como estperado.", election.getStatus() == false);
  }

  @Test
  public void testEndSessionWrong() {

    certifiedProfessional.startSession(election, electionPassword);
    Throwable exception = assertThrows(Throwable.class, () -> {
      certifiedProfessional.endSession(election, "12");
    });

    String expectedMessage = "Senha inválida";
    String actualMessage = exception.getMessage();

    assertTrue("A exception de senha incorreta não foi lançada como esperado.", expectedMessage.equals(actualMessage));
  }

  @Test
  public void testShowFinalResult(){
    Voter v1 = new Voter.Builder().name("v1").electoralCard("123456789012").state("MG").build();
    Voter v2 = new Voter.Builder().name("v2").electoralCard("223456789022").state("MG").build();
    Voter v3 = new Voter.Builder().name("v3").electoralCard("333456789033").state("MG").build();

    President presidentCandidate1 = new President.Builder().name("João").number(123).party("PDS1").build();
    election.get("Presidente").addCandidate(presidentCandidate1, electionPassword);
    President presidentCandidate2 = new President.Builder().name("Maria").number(124).party("ED").build();
    election.get("Presidente").addCandidate(presidentCandidate2, electionPassword);
    
    FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder().name("Carlos").number(12345).party("PDS1")
        .state("MG").build();  
    election.get("Deputado Federal").addCandidate(federalDeputyCandidate1, electionPassword);
    FederalDeputy federalDeputyCandidate2 = new FederalDeputy.Builder().name("Cleber").number(54321).party("PDS2")
        .state("MG").build();
    election.get("Deputado Federal").addCandidate(federalDeputyCandidate2, electionPassword);
    FederalDeputy federalDeputyCandidate3 = new FederalDeputy.Builder().name("Sofia").number(11211).party("IHC")
        .state("MG").build();
    election.get("Deputado Federal").addCandidate(federalDeputyCandidate3, electionPassword);

    election.start(electionPassword);

    v1.vote("123", election, "Presidente", false,false);
    v2.vote("123", election, "Presidente", false,false);
    v3.vote("124", election, "Presidente", false,false);
    
    v1.vote("0000", election, "Deputado Federal", false, true);
    v2.vote("12345", election, "Deputado Federal", false,false);
    v3.vote("0", election, "Deputado Federal", true, false);
    System.out.println("___________________________________________________");
    String ans = "Resultado da eleicao:  Votos presidente:  Total: 3  Votos nulos: 0 (0,00%)  Votos brancos: 0 (0,00%)"+
    "Numero - Partido - Nome  - Votos  - % dos votos totais123 - PDS1 - João - 2 - 66,67%124 - ED - Maria - 1 - 33,33%"+
    "  Presidente eleito:  João do PDS1 com 66,67% dos votos=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-="+
    "Resultado da eleicao:  Votos para Federal Deputy:  Votos nulos: 1 (33,33%)  Votos brancos: 1 (33,33%)" +
    "  Total: 3Numero - Partido - Nome - Estado - Votos - % dos votos totais12345 - PDS1 - MG - Carlos - 1 - 33,33%54321 - PDS2 - MG - Cleber - 0 - 0,00%"+
    "11211 - IHC - MG - Sofia - 0 - 0,00%Federal Deputy eleitos:  1º Carlos do PDS1 com 33,33% dos votos  2º Cleber do PDS2 com 0,00% dos votos";
    
    election.finish(electionPassword);
    assertTrue("Resultado Incorreto para eleição criada", election.getResults(electionPassword).replace("\n", "").replace("\r", "").replace("\t", "").contains(ans));
	}
}