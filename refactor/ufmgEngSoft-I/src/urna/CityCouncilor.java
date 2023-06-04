package urna;
// #if Federal || Municipal

public class CityCouncilor extends DistrictalLegislativeCandidate{
      
    public CityCouncilor(Builder builder) {
        super(builder);
    }

    public static class Builder extends DistrictalLegislativeCandidate.Builder<Builder>{
        
        @Override
        public Builder getThis(){
            return this;
        }

        public CityCouncilor build() {
            return new CityCouncilor(this);
        }

    }    

};

// #endif
