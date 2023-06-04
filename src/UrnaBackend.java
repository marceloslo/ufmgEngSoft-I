package urna;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class UrnaBackend {

  private static final Map<String, TSEProfessional> TSEMap = UrnaDatabase.loadTSEProfessionals();

  private static final Map<String, Voter> VoterMap = UrnaDatabase.loadVoters("bemlegal.txt");

  private static MultipleElections currentElection;
  
  private Voter voter;
  
  public UrnaBackend(String electionPassword) {

    currentElection = MultipleElections.getInstance();

    // #if Federal
    currentElection.addElection("President", new PoliticalElection(electionPassword));
    currentElection.addElection("FederalDeputy", new PoliticalElection(electionPassword));
    currentElection.addElection("StateDeputy", new PoliticalElection(electionPassword));
    currentElection.addElection("Senator", new PoliticalElection(electionPassword));
    currentElection.addElection("Governor", new PoliticalElection(electionPassword));
    // #endif
    
    // #if Municipal
//@    currentElection.addElection("Prefeito", new PoliticalElection(electionPassword));
//@    currentElection.addElection("Vereador", new PoliticalElection(electionPassword));
    // #endif
    
    // #if RealityShow
//@    currentElection.addElection("Participante Reality", new PoliticalElection(electionPassword));
    // #endif

    // #if University
//@    currentElection.addElection("UniversityCandidate", new UniversityElection(electionPassword));
    // #endif

    UrnaDatabase.loadCandidates(currentElection, electionPassword);
  }
	
  
  public void vote(String voteNumber, String role) {
	  voter.vote(voteNumber, currentElection, role, false, false);
  }
  
  public void protestVote(String role) {
	  voter.vote("0", currentElection, role, true, false);
  }
  
  public void NullVote(String role) {
	  voter.vote("0", currentElection, role, false, true);
  }

  public President getPresidentByNumber(int voteNumber) {
	  return (President) currentElection.get("President").getCandidateByNumber(Integer.toString(voteNumber));
  }

  public Governor getGovernorByNumber(String state,int voteNumber) {
    return (Governor) currentElection.get("Governor").getCandidateByNumber(state+Integer.toString(voteNumber));
  }
  
  public FederalDeputy getFederalDeputyByNumber(String state,int voteNumber) {
	  return (FederalDeputy) currentElection.get("FederalDeputy").getCandidateByNumber(state+Integer.toString(voteNumber));
  }

  public StateDeputy getStateDeputyByNumber(String state,int voteNumber) {
    return (StateDeputy) currentElection.get("StateDeputy").getCandidateByNumber(state+Integer.toString(voteNumber));
  }

  public Senator getSenatorByNumber(String state,int voteNumber) {
    return (Senator) currentElection.get("Senator").getCandidateByNumber(state+Integer.toString(voteNumber));
  }

  // DISTRICT

  public Mayor getMayorByNumber(String district, int voteNumber) {
    return (Mayor) currentElection.get("Prefeito").getCandidateByNumber(district+Integer.toString(voteNumber));
  }

  public CityCouncilor getCityCouncilorByNumber(String district, int voteNumber) {
    return (CityCouncilor) currentElection.get("Vereador").getCandidateByNumber(district+Integer.toString(voteNumber));
  }

  // REALITY

  public RealityCandidate getRealityCandidateByNumber(String nationality, int voteNumber) {
    return (RealityCandidate) currentElection.get("Participante Reality").getCandidateByNumber(nationality+Integer.toString(voteNumber));
  }

  // UNIVERSITY

  public DepartmentHead getUniversityCandidateByNumber(string Department, int voteNumber) {
    return (DepartmentHead) currentElection.get("UniversityCandidate").getCandidateByNumber(Department+Integer.toString(voteNumber));
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
  
  // #if SegundoTurno
//@  public List<String> electionsWithNoSecondRound() {
//@	  List<String> noSecondRound = new ArrayList<>();
//@	  for(var entry : currentElection.secondRounds.entrySet()) {
//@		  if(entry.getValue() == false) {
//@			  noSecondRound.add(entry.getKey());
//@		  }
//@	  }
//@	  return noSecondRound;
//@  }
  // #endif


}
