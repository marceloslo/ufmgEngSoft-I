import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;


import urna.CertifiedProfessional;
import urna.Election;
import urna.President;
import urna.FederalDeputy;
import urna.Voter;

public class CertifiedProfessionalTest {
  CertifiedProfessional certifiedProfessional;
  String user;
  String password;
  Election election;
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
	Election.reset();
	election = Election.getInstance(electionPassword);
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
    election.addPresidentCandidate(presidentCandidate1, electionPassword);
    President presidentCandidate2 = new President.Builder().name("Maria").number(124).party("ED").build();
    election.addPresidentCandidate(presidentCandidate2, electionPassword);
    FederalDeputy federalDeputyCandidate1 = new FederalDeputy.Builder().name("Carlos").number(12345).party("PDS1")
        .state("MG").build();
    election.addFederalDeputyCandidate(federalDeputyCandidate1, electionPassword);
    FederalDeputy federalDeputyCandidate2 = new FederalDeputy.Builder().name("Cleber").number(54321).party("PDS2")
        .state("MG").build();
    election.addFederalDeputyCandidate(federalDeputyCandidate2, electionPassword);
    FederalDeputy federalDeputyCandidate3 = new FederalDeputy.Builder().name("Sofia").number(11211).party("IHC")
        .state("MG").build();
    election.addFederalDeputyCandidate(federalDeputyCandidate3, electionPassword);

    election.start(electionPassword);

    v1.vote(123, election, "President", false);
    v2.vote(123, election, "President", false);
    v3.vote(124, election, "President", false);
    v1.vote(12345, election, "FederalDeputy", false);
    v1.vote(0000, election, "FederalDeputy", false);
    v2.vote(12345, election, "FederalDeputy", false);
    v2.vote(54321, election, "FederalDeputy", false);
    v3.vote(12345, election, "FederalDeputy", false);
    v3.vote(0, election, "FederalDeputy", true);
    System.out.println("___________________________________________________");
    String ans = "";
    ans += "Resultado da eleicao:";
    ans += "  Votos presidente:";
    ans += "  Total: 3";
    ans += "  Votos nulos: 0 (0,00%)";
    ans += "  Votos brancos: 0 (0,00%)";
    ans += "Numero - Partido - Nome  - Votos  - % dos votos totais";
    ans += "123 - PDS1 - João - 2 - 66,67%";
    ans += "124 - ED - Maria - 1 - 33,33%";
    ans += "  Presidente eleito:";
    ans += "  João do PDS1 com 66,67% dos votos";
    ans += "=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=";
    ans += "  Votos deputado federal:";
    ans += "  Votos nulos: 1 (16,67%)";
    ans += "  Votos brancos: 1 (16,67%)";
    ans += "  Total: 6";
    ans += "Numero - Partido - Nome - Estado - Votos - % dos votos totais";
    ans += "12345 - PDS1 - MG - Carlos - 3 - 50,00%";
    ans += "54321 - PDS2 - MG - Cleber - 1 - 16,67%";
    ans += "11211 - IHC - MG - Sofia - 0 - 0,00%";
    ans += "  Deputados eleitos:";
    ans += "  1º Carlos do PDS1 com 50,00% dos votos";
    ans += "  2º Cleber do PDS2 com 16,67% dos votos";
    
    election.finish(electionPassword);
    System.out.println(election.getResults(electionPassword).replace("\n", "").replace("\r", "").replace("\t", ""));
    System.out.println(ans);
    assertTrue("Resultado Incorreto para eleição criada", election.getResults(electionPassword).replace("\n", "").replace("\r", "").replace("\t", "").contains(ans));
	}
}