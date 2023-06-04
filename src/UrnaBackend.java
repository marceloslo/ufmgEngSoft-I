package urna;

import java.util.Map;

import java.util.List;
import java.util.ArrayList;

public class UrnaBackend {

  private static final Map<String, TSEProfessional> TSEMap = UrnaDatabase.loadTSEProfessionals();

  private static Map<String, Voter> VoterMap = null;

  private static MultipleElections currentElection;
  
  private Voter voter;
  
  public UrnaBackend(String electionPassword) {

    currentElection = MultipleElections.getInstance(electionPassword);

    // #if Federal
    currentElection.addElection("President", new PoliticalElection(electionPassword));
    currentElection.addElection("FederalDeputy", new PoliticalElection(electionPassword));
    currentElection.addElection("StateDeputy", new PoliticalElection(electionPassword));
    currentElection.addElection("Senator", new PoliticalElection(electionPassword));
    currentElection.addElection("Governor", new PoliticalElection(electionPassword));
    VoterMap = UrnaDatabase.loadVoters("bemlegal.txt");
    // #endif
    
    // #if Municipal
//@    currentElection.addElection("Mayor", new PoliticalElection(electionPassword));
//@    currentElection.addElection("CityCouncilor", new PoliticalElection(electionPassword));
//@    VoterMap = UrnaDatabase.loadVoters("bemlegal.txt");
    // #endif
    
    // #if RealityShow
//@    currentElection.addElection("RealityCandidate", new RealityElection(electionPassword));
//@    VoterMap = UrnaDatabase.loadVoters("bemlegal.txt");
    // #endif

    // #if Universidade
//@    currentElection.addElection("DepartmentHead", new UniversityElection("Chefe de Departamento", electionPassword));
//@    currentElection.addElection("Chancellor", new UniversityElection("Reitor", electionPassword));
//@    VoterMap = UrnaDatabase.loadVoters("bemlegal.txt");
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

  // #if Federal
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

  // #endif
  
  // #if Municipal
//@
//@  public Mayor getMayorByNumber(String district, int voteNumber) {
//@    return (Mayor) currentElection.get("Mayor").getCandidateByNumber(district+Integer.toString(voteNumber));
//@  }
//@
//@  public CityCouncilor getCityCouncilorByNumber(String district, int voteNumber) {
//@    return (CityCouncilor) currentElection.get("CityCouncilor").getCandidateByNumber(district+Integer.toString(voteNumber));
//@  }
//@
  // #endif

  // #if RealityShow
//@  public RealityCandidate getRealityCandidateByNumber(String nationality, int voteNumber) {
//@    return (RealityCandidate) currentElection.get("RealityCandidate").getCandidateByNumber(nationality+Integer.toString(voteNumber));
//@  }
  // #endif

  // #if Universidade
//@
//@  public UniversityCandidate getDepartmentHeadByNumber(int voteNumber) {
//@    return (UniversityCandidate) currentElection.get("DepartmentHead").getCandidateByNumber(Integer.toString(voteNumber));
//@  }
//@
//@  public UniversityCandidate getChancellorByNumber(int voteNumber) {
//@    return (UniversityCandidate) currentElection.get("Chancellor").getCandidateByNumber(Integer.toString(voteNumber));
//@  }
  // #endif
  
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
  public List<String> electionsWithNoSecondRound() {
	  List<String> noSecondRound = new ArrayList<>();
	  for(var entry : currentElection.secondRounds.entrySet()) {
		  if(entry.getValue() == false) {
			  noSecondRound.add(entry.getKey());
		  }
	  }
	  return noSecondRound;
  }
  // #endif


}
