## Execução

### profile "local" - Embedded Container

Com o profile default, o sistema executa com container embedded e user domain em memória.

    mvn -Plocal clean spring-boot:run -Dspring.profiles.active=local
    
### profile 'wildfly' - WAR Deployment no Wildfly

Com o profile wildfly, o sistema executa a partir de um container externo e user domain do container.

Para fazer deployment/undeployment através da CLI, configure em seu arquivo ~.m2/settings.xml as seguintes propriedades do Wildfly: 

    wildfly.hostname
    wildfly.port
    wildfly.username
    wildfly.password

E execute o seguinte comando:

    mvn -Pwildfly clean wildfly:deploy

*NOTA* O profile do Spring está sendo atribuído através de uma variável JNDI definida no web.xml.

## Configurações

Criar os grupos/roles com o prefixo ROLE_ por exemplo:

    ROLE_WEBSERVICE
    ROLE_WEB
    ROLE_ADMIN

**NOTA** Os grupos no seu backend (LDAP, database, etc) não precisam estar prefixados uma vez que podem ser mapeados em tempo de deployment através de um de-para no arquivo jboss-web.xml.

No web.xml os grupos podem ser mapeados, preferencialmente, apenas com o nome (WEBSERVICE, WEB, ADMIN) ou com o prefixo completo (ROLE_WEBSERVICE, ROLE_WEB, ROLE_ADMIN). 

Na WebSecurityConfigurerAdapter os grupos devem ser mapeados apenas com o nome (WEBSERVICE, WEB, ADMIN) quando utilizados no método que especificam roles como roles() ou mappableRoles(), e devem ser mapeados com o prefixo completo (ROLE_WEBSERVICE, ROLE_WEB, ROLE_ADMIN) nos métodos que especificam authority como hasAuthority().

### web.xml

Apesar do filtro poder ser declarado através de AbstractSecurityWebApplicationInitializer, foi declarado no mecanismo XML padrão.

    <filter>
		<filter-name>springSecurityFilterChain</filter-name>
		<filter-class>org.springframework.web.filter.DelegatingFilterProxy</filter-class>
	</filter>

	<filter-mapping>
		<filter-name>springSecurityFilterChain</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>

	<login-config>
		<auth-method>BASIC</auth-method>
		<realm-name>some user domain</realm-name>
	</login-config>

	<security-role>
		<role-name>ROLE_GUEST</role-name>
	</security-role>

	<security-role>
		<role-name>ROLE_ADMIN</role-name>
	</security-role>

### jboss-web.xml

    <?xml version="1.0" encoding="UTF-8"?>
    <jboss-web>
        <context-root>/poc</context-root>
        <security-domain>java:/jaas/other</security-domain>
    </jboss-web>

### Resources

As resources e sub-resources podem acessar o principal e verificar roles através do contexto de segurança:

	@javax.ws.rs.core.Context
	protected SecurityContext securityContext;

	Principal principal = securityContext.getUserPrincipal();
	boolean admin = securityContext.isUserInRole("ADMIN");

Também é possível criar regras de autorização diretamente no método:

	@javax.annotation.security.RolesAllowed("ADMIN")
	@GET
	@Path("/ws/private/resource")

## Testing

Adicione os seguintes usuários no ApplicationRealm:

	/opt/wildfly/bin/add-user.sh -a -u user1 -p user@123 -g ROLE_GUEST -r ApplicationRealm
	/opt/wildfly/bin/add-user.sh -a -u admin1 -p admin@123 -g ROLE_GUEST,ROLE_ADMIN -r ApplicationRealm

### profile "local" - Embedded Container

Retorno 200 OK

    curl --basic -u user1:user@123 http://localhost:8080/poc/guest

Retorno 403 Forbidden

    curl --basic -u user1:user@123 http://localhost:8080/poc/admin

Retorno 200 OK

    curl --basic -u admin1:admin@123 http://localhost:8080/poc/guest

Retorno 200 OK

    curl --basic -u admin1:admin@123 http://localhost:8080/poc/admin

