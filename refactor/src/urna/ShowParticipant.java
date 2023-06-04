package urna;

public class ShowParticipant extends DistrictalLegislativeCandidate{
      
    public ShowParticipant(Builder builder) {
        super(builder);
    }

    public static class Builder extends DistrictalLegislativeCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public ShowParticipant build() {
            return new ShowParticipant(this);
        }

    }    

};
