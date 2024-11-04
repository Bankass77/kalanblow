package ml.kalanblow.gestiondesinscriptions.util;

import org.testcontainers.containers.PostgreSQLContainer;

public class KalanblowPostgresqlContainer extends PostgreSQLContainer<KalanblowPostgresqlContainer> {
    private  static final String IMAGE_VERSION= "postgres:16.4";
    private static  KalanblowPostgresqlContainer container;

    private  KalanblowPostgresqlContainer() {
        super(IMAGE_VERSION);
    }

    public static KalanblowPostgresqlContainer getInstance(){
        if (container == null){
            container = new KalanblowPostgresqlContainer();
        }
        return  container;
    }

    @Override
    public void start() {
        super.start();
        System.setProperty("DB_URL", container.getJdbcUrl());
        System.setProperty("DB_USERNAME", container.getUsername());
        System.setProperty("DB_PASSWORD", container.getPassword());
        //System.setProperty("spring.datasource.driver-class-name", container.getDriverClassName());
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }
}
