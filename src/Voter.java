package urna;
public class Voter {
  protected final String electoralCard;

  protected final String name;

  protected final String state;

  protected final String nationality;
  protected final String district;

  public static class Builder {
    private String identification;
    private String name;
    private String state;
    private String nationality;
    private String district;

    public Builder electoralCard(String electoralCard) {
      this.identification = electoralCard;
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

    public Builder district(String district) {
      this.district = district;
      return this;
    }
    
    public Builder nationality(String nationality) {
      this.nationality = nationality;
      return this;      
      
    }
    public Voter build() {
      if (identification == null)
        throw new IllegalArgumentException("electoralCard mustn't be null");

      if (identification.isEmpty())
        throw new IllegalArgumentException("electoralCard mustn't be empty");

      if (name == null)
        throw new IllegalArgumentException("name mustn't be null");

      if (name.isEmpty())
        throw new IllegalArgumentException("name mustn't be empty");

      if (state == null)
        throw new IllegalArgumentException("state mustn't be null");

      if (state.isEmpty())
        throw new IllegalArgumentException("state mustn't be empty");

      return new Voter(identification, name, state, district, nationality);
    }
  }

  protected Voter(String electoralCard, String name, String state, String district, String nationality) {
    this.electoralCard = electoralCard;
    this.name = name;
    this.state = state;
    this.district = district;
    this.nationality = nationality;
  }

  public void vote(String number, MultipleElections election, String type, Boolean isProtestVote, Boolean isNullVote) {
    if (isProtestVote)
      election.get(type).computeProtestVote(this);
    else if (isNullVote)
      election.get(type).computeNullVote(this);
    else {
      // add state to code
      if(type == "Deputado Federal" || type == "Deputado Estadual" || type == "Senador" || type == "Governador"){ 
        number = this.state+number;
      }
      if (type == "Prefeito" || type == "Vereador"){
        number = this.district+number;
      }
      if (type == "Participante Reality"){
        number = this.nationality+number;
      }

      Candidate candidate = election.get(type).getCandidateByNumber(number);
      if (candidate == null){
        election.get(type).computeNullVote(this);
        throw new Warning("Número de candidato inválido. Voto nulo computado.");
      }
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

  public String getDistrict() {
  return district;
  }

  public String getNationality() {
  return nationality;
  }
}
