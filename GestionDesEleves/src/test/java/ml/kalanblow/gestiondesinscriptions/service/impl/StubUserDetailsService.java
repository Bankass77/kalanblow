package ml.kalanblow.gestiondesinscriptions.service.impl;

import com.github.javafaker.Faker;
import ml.kalanblow.gestiondesinscriptions.enums.Gender;
import ml.kalanblow.gestiondesinscriptions.enums.MaritalStatus;
import ml.kalanblow.gestiondesinscriptions.enums.UserRole;
import ml.kalanblow.gestiondesinscriptions.model.*;
import ml.kalanblow.gestiondesinscriptions.security.KalanblowUserDetails;
import org.junit.jupiter.api.Disabled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.*;
@Disabled
public class StubUserDetailsService  implements UserDetailsService {

    public static final String USERNAME_USER = "admintest@exemple.fr";

    private final Faker faker = new Faker(new Locale("fr"));

    private  final Map<String, KalanblowUserDetails> users= new HashMap<>();

    public StubUserDetailsService (BCryptPasswordEncoder passwordEncoder){

        addUser(new KalanblowUserDetails(createEleve(passwordEncoder)));

    }


    /**
     * Adds a user with the specified details to the system.
     *
     * @param kalanblowUserDetails The details of the user to be added.
     */
    private void addUser(KalanblowUserDetails kalanblowUserDetails){

        users.put(kalanblowUserDetails.getUsername(), kalanblowUserDetails);
    }
    /**
     * Locates the user based on the username. In the actual implementation, the search
     * may possibly be case sensitive, or case insensitive depending on how the
     * implementation instance is configured. In this case, the <code>UserDetails</code>
     * object that comes back may have a username that is of a different case than what
     * was actually requested..
     *
     * @param username the username identifying the user whose data is required.
     * @return a fully populated user record (never <code>null</code>)
     * @throws UsernameNotFoundException if the user could not be found or the user has no
     *                                   GrantedAuthority
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return Optional.ofNullable(users.get(username)).orElseThrow(()-> new UsernameNotFoundException(username));
    }



    /**
     * Creates an instance of the Eleve class with encrypted password using the provided BCryptPasswordEncoder.
     *
     * @param passwordEncoder The BCryptPasswordEncoder used to encrypt the password.
     * @return A new Eleve instance with the encrypted password.
     */
    private Eleve createEleve(BCryptPasswordEncoder passwordEncoder){
        Address address = new Address();
        address.setStreetNumber(Integer.valueOf(faker.address().streetAddressNumber()));
        address.setStreet(faker.address().streetAddress());
        address.setCountry(faker.address().country());
        address.setCity(faker.address().city());
        address.setCodePostale(Integer.valueOf(faker.address().zipCode()));
        PhoneNumber phoneNumber = new PhoneNumber(faker.phoneNumber().phoneNumber());
        Set<UserRole> userRoles= new HashSet<>();
        UserRole userRole= UserRole.STUDENT;
        UserRole adminRole= UserRole.ADMIN;
        userRoles.add(userRole);
        userRoles.add(adminRole);
        Eleve eleve= new Eleve();
        eleve.setUserName(new UserName( "Admin", "Admin"))
                .setPassword(passwordEncoder.encode("Homeboarding2014&")).setGender(Gender.MALE).
                setCreatedDate(LocalDateTime.now()).setMaritalStatus(MaritalStatus.SINGLE).setEmail(new Email("admintest@exemple.fr"))
                .setLastModifiedDate(LocalDateTime.now()).setPhoneNumber(phoneNumber).setAddress(address).setRoles(userRoles);
        return  eleve;
    }
}
