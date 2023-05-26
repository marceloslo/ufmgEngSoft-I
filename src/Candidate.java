package urna;
public class Candidate {
  protected final String name;

  protected final String party;

  protected final int number;

  protected int numVotes;

  public Candidate(
      String name,
      String party,
      int number) {

    if (name == null)
      throw new IllegalArgumentException("name mustn't be null");

    if (name.isEmpty())
      throw new IllegalArgumentException("name mustn't be empty");

    if (party == null)
      throw new IllegalArgumentException("party mustn't be empty");

    if (party.isEmpty())
      throw new IllegalArgumentException("party mustn't be empty");

    if (number <= 0)
      throw new IllegalArgumentException("number must be greater or equal to 1");

    this.name = name;
    this.party = party;
    this.number = number;
    this.numVotes = 0;
  }

  public String getName() {
    return this.name;
  }

  public String getParty() {
    return this.party;
  }

  public int getNumber() {
    return this.number;
  }
}
