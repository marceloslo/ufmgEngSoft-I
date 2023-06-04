package urna;

public class Mayor extends DistrictalLegislativeCandidate{
      
    public Mayor(Builder builder) {
        super(builder);
    }

    public static class Builder extends DistrictalLegislativeCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public Mayor build() {
            return new Mayor(this);
        }

    }    

};