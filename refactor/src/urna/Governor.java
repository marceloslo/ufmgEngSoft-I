package urna;

public class Governor extends FederalLegislativeCandidate{
      
    public Governor(Builder builder) {
        super(builder);
    }

    public static class Builder extends FederalLegislativeCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public Governor build() {
            return new Governor(this);
        }

    }    

};
