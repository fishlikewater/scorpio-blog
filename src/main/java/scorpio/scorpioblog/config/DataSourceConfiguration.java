package scorpio.scorpioblog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sql2o.Sql2o;
import org.sqlite.SQLiteDataSource;
import scorpio.BaseUtils;

import javax.sql.DataSource;

@Configuration
@ConditionalOnClass(DataSource.class)
public class DataSourceConfiguration {

    @Value("${spring.datasource.driverClassName}")
    private String driverClass;
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${scorpio.debug:false}")
    private String debug;

    @Bean(destroyMethod = "", name = "sqLiteDataSource")
    public DataSource dataSource() {
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName(driverClass);
        dataSourceBuilder.url(url);
        dataSourceBuilder.type(SQLiteDataSource.class);
        return dataSourceBuilder.build();
    }

    @Bean
    public Sql2o sql2o(){
        BaseUtils.debug = Boolean.valueOf(debug);
        return BaseUtils.open(dataSource());
    }
}
