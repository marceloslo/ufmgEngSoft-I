package urna;

import java.util.Map;

public class UrnaBackend {

  private static final Map<String, TSEProfessional> TSEMap = UrnaDatabase.loadTSEProfessionals();

  private static final Map<String, Voter> VoterMap = UrnaDatabase.loadVoters("voterLoad.txt");

  private static MultipleElections currentElection;
  
  private Voter voter;
  
  public UrnaBackend(String electionPassword) {

    currentElection = MultipleElections.getInstance();

    currentElection.addElection("President", new PoliticalElection(electionPassword));
    currentElection.addElection("FederalDeputy", new PoliticalElection(electionPassword));

    UrnaDatabase.loadCandidates(currentElection, electionPassword);
  }
	
  
  public void vote(String voteNumber, String role) {
	  voter.vote(voteNumber, currentElection, role, false);
  }
  
  public void protestVote(String role) {
	  voter.vote("0", currentElection, role, true);
  }
  
  // FEDERAL
  

  public President getPresidentByNumber(int voteNumber) {
	  return (President) currentElection.get("President").getCandidateByNumber(Integer.toString(voteNumber));
  }

  public Governor getGovernorByNumber(String state,int voteNumber) {
    return (Governor) currentElection.get("Governor").getCandidateByNumber(state+voteNumber);
  }
  
  public FederalDeputy getFederalDeputyByNumber(String state,int voteNumber) {
	  return (FederalDeputy) currentElection.get("FederalDeputy").getCandidateByNumber(state+voteNumber);
  }

  public StateDeputy getStateDeputyByNumber(String state,int voteNumber) {
    return (StateDeputy) currentElection.get("StateDeputy").getCandidateByNumber(state+voteNumber);
  }

  public Senator getSenatorByNumber(String state,int voteNumber) {
    return (Senator) currentElection.get("Senator").getCandidateByNumber(state+voteNumber);
  }

  // DISTRICT

  public Mayor getMayorByNumber(String district, int voteNumber) {
    return (Mayor) currentElection.get("Mayor").getCandidateByNumber(district+voteNumber);
  }

  public CityCouncilor getCityCouncilorByNumber(String district, int voteNumber) {
    return (CityCouncilor) currentElection.get("CityCouncilor").getCandidateByNumber(district+voteNumber);
  }


  
  public Voter getVoter() {
	return voter;
  }
  
  public Voter getVoter(String electoralCard) {
	voter = VoterMap.get(electoralCard);
    return voter;
  }
  
  public Boolean getElectionStatus() {
	  return currentElection.getStatus();
  }
  
  public TSEProfessional getTseProfessional(String user) {
    return TSEMap.get(user);
  }

  public void addCandidate(TSEEmployee tseProfessional, Candidate candidate, String pwd) {
    tseProfessional.addCandidate(candidate, currentElection, pwd);
  }


  public void removeCandidate(TSEEmployee tseProfessional, Candidate candidate, String pwd) {
    tseProfessional.removeCandidate(candidate, currentElection, pwd);
  }


  public void startSession(CertifiedProfessional tseProfessional, String pwd) {
    tseProfessional.startSession(currentElection, pwd);
  }
  
  public void endSession(CertifiedProfessional tseProfessional, String pwd) {
    tseProfessional.endSession(currentElection, pwd);
  }


  public String getFinalResult(CertifiedProfessional tseProfessional, String pwd) {
    return tseProfessional.getFinalResult(currentElection, pwd);
  }
  
  /*public Boolean isSecondRound() {
	  return currentElection.segundoTurno;
  }*/


}
