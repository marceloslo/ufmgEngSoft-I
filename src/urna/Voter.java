package urna;
public class Voter {
  protected final String electoralCard;

  protected final String name;

  protected final String state;

  public static class Builder {
    private String electoralCard;
    private String name;
    private String state;

    public Builder electoralCard(String electoralCard) {
      this.electoralCard = electoralCard;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder state(String state) {
      this.state = state;
      return this;
    }

    public Voter build() {
      if (electoralCard == null)
        throw new IllegalArgumentException("electoralCard mustn't be null");

      if (electoralCard.isEmpty())
        throw new IllegalArgumentException("electoralCard mustn't be empty");

      if (name == null)
        throw new IllegalArgumentException("name mustn't be null");

      if (name.isEmpty())
        throw new IllegalArgumentException("name mustn't be empty");

      if (state == null)
        throw new IllegalArgumentException("state mustn't be null");

      if (state.isEmpty())
        throw new IllegalArgumentException("state mustn't be empty");

      return new Voter(electoralCard, name, state);
    }
  }

  protected Voter(String electoralCard, String name, String state) {
    this.electoralCard = electoralCard;
    this.name = name;
    this.state = state;
  }

  public void vote(String number, MultipleElections election, String type, Boolean isProtestVote) {
    if (isProtestVote)
      election.get(type).computeProtestVote(this);
    else if (number == "0")
      election.get(type).computeNullVote(this);
    else {
      // add state to code
      if(type == "FederalDeputy"){
        number = this.state+number;
      }
      Candidate candidate = election.get(type).getCandidateByNumber(number);
      if (candidate == null)
        throw new Warning("Número de candidato inválido");
      election.get(type).computeVote(candidate, this);
    }
  }

  public String getElectoralCard() {
	return electoralCard;
  }

  public String getName() {
	return name;
  }

  public String getState() {
 	return state;
  }
}
