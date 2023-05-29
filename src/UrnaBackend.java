package urna;

import java.util.Map;

public class UrnaBackend {

  private static final Map<String, TSEProfessional> TSEMap = UrnaDatabase.loadTSEProfessionals();

  private static final Map<String, Voter> VoterMap = UrnaDatabase.loadVoters("voterLoad.txt");

  private static Election currentElection;
  
  private Voter voter;
  
  public UrnaBackend(String electionPassword) {
    currentElection = Election.getInstance(electionPassword);

    UrnaDatabase.loadCandidates(currentElection, electionPassword);
  }
	
  
  public void vote(Voter voter,int voteNumber,String role) {
	  voter.vote(voteNumber, currentElection, role, false);
  }
  
  public void protestVote(Voter voter,String role) {
	  voter.vote(0, currentElection, role, true);
  }
  
  public President getPresidentByNumber(int voteNumber) {
	  return currentElection.getPresidentByNumber(voteNumber);
  }
  
  public FederalDeputy getFederalDeputyByNumber(String state,int voteNumber) {
	  return currentElection.getFederalDeputyByNumber(state,voteNumber);
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


}
