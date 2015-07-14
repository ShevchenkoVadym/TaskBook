package ru.javarush.taskbook.model;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUserDetails;

import javax.persistence.*;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.*;


@Entity
@Table(name = "users")
public class User implements SocialUserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    private String username;

    @Column(name = "full_name", length = 100, nullable = true)
    private String fullName;

    @Column(name = "password")
    private String password;

    @Column(name = "is_enabled")
    private boolean isEnabled;

    @Column(name = "is_confirmed")
    private boolean isConfirmed;

    @Column(name = "is_non_read_only")
    private boolean isNonReadOnly;

    @Column(name = "creation_date")
    private Date creationDate;

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    /*
        @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
        @JoinTable(name= "user_role",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<UserRole> userRoles = new HashSet<>();
    */

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER )
    @CollectionTable(name = "roles",
            joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role_id")
    private Set<Role> roles = new HashSet<>();

    @Column(name = "country")
    private String country;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name="rating")
    private double rating;

    @Column(name="tasks_solved")
    private int tasksSolved;

    @Enumerated(EnumType.STRING)
    @Column(name = "sign_in_provider", length = 20)
    private SocialMediaService signInProvider;

    @Column(name = "token_confirmation", length = 255)
    private String tokenConfirmation;

    @Column(name = "last_visit")
    private Date lastVisit;

    public static String generatePassword() {
        String charset = "0123456789abcdefghijklmnopqrstuvwxyz";
        Random rand = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < charset.length(); i++) {
            int pos = rand.nextInt(charset.length());
            sb.append(charset.charAt(pos));
        }
        return sb.toString();
    }
    
    public User() {
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
/*
        Set<String> roles = this.getRoles();
        if (roles == null) {
            return Collections.emptyList();
        }
*/
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.toString()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isEnabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isEnabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isEnabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {return fullName;}

    public void setFullName(String fullName) {this.fullName = fullName;}

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public boolean isNonReadOnly() {
        return isNonReadOnly;
    }

    public void setNonReadOnly(boolean isNonReadOnly) {
        this.isNonReadOnly = isNonReadOnly;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getTasksSolved() {
        return tasksSolved;
    }

    public void setTasksSolved(int tasksSolved) {
        this.tasksSolved = tasksSolved;
    }

    public SocialMediaService getSignInProvider() {
        return signInProvider;
    }

    public void setSignInProvider(SocialMediaService signInProvider) {
        this.signInProvider = signInProvider;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getTokenConfirmation() { return tokenConfirmation; }

    public void setTokenConfirmation(String tokenConfirmation) { this.tokenConfirmation = tokenConfirmation; }

    public Date getLastVisit() {
        return lastVisit;
    }

    public void setLastVisit(Date lastVisit) {
        this.lastVisit = lastVisit;
    }

    public void generateToken() {
        SecureRandom random = new SecureRandom();
        this.tokenConfirmation = new BigInteger(130, random).toString(32);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", isNonReadOnly=" + isNonReadOnly +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", country='" + country + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", rating=" + rating +
                ", tasksSolved=" + tasksSolved +
                ", signInProvider=" + signInProvider +
                ", tokenConfirmation=" + tokenConfirmation +
                ", creationDate=" + creationDate +
                '}';
    }

/*    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", isNonReadOnly=" + isNonReadOnly +
                ", creationDate=" + creationDate +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", country='" + country + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", rating=" + rating +
                ", tasksSolved=" + tasksSolved +
                ", signInProvider=" + signInProvider +
                ", tokenConfirmation='" + tokenConfirmation + '\'' +
                ", lastVisit=" + lastVisit +
                '}';
    }*/

/*    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", password='" + password + '\'' +
                ", isEnabled=" + isEnabled +
                ", isConfirmed=" + isConfirmed +
                ", isNonReadOnly=" + isNonReadOnly +
                ", creationDate=" + creationDate +
                ", email='" + email + '\'' +
                ", roles=" + roles +
                ", country='" + country + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", rating=" + rating +
                ", tasksSolved=" + tasksSolved +
                ", signInProvider=" + signInProvider +
                ", tokenConfirmation='" + tokenConfirmation + '\'' +
                ", lastVisit=" + lastVisit +
                '}';
    }*/

    @Override
    public String getUserId() {
        return email;
    }

/*    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isEnabled != user.isEnabled) return false;
        if (isNonReadOnly != user.isNonReadOnly) return false;
        if (Double.compare(user.rating, rating) != 0) return false;
        if (tasksSolved != user.tasksSolved) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        if (!email.equals(user.email)) return false;
        if (!imageUrl.equals(user.imageUrl)) return false;
        if (!roles.equals(user.roles)) return false;
        if (signInProvider != user.signInProvider) return false;
        if (!username.equals(user.username)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = username.hashCode();
        result = 31 * result + (isEnabled ? 1 : 0);
        result = 31 * result + (isNonReadOnly ? 1 : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + roles.hashCode();
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + imageUrl.hashCode();
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + tasksSolved;
        result = 31 * result + (signInProvider != null ? signInProvider.hashCode() : 0);
        return result;
    }*/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (isConfirmed != user.isConfirmed) return false;
        if (isEnabled != user.isEnabled) return false;
        if (isNonReadOnly != user.isNonReadOnly) return false;
        if (Double.compare(user.rating, rating) != 0) return false;
        if (tasksSolved != user.tasksSolved) return false;
        if (country != null ? !country.equals(user.country) : user.country != null) return false;
        if (creationDate != null ? !creationDate.equals(user.creationDate) : user.creationDate != null) return false;
        if (email != null ? !email.equals(user.email) : user.email != null) return false;
        if (fullName != null ? !fullName.equals(user.fullName) : user.fullName != null) return false;
        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (imageUrl != null ? !imageUrl.equals(user.imageUrl) : user.imageUrl != null) return false;
        if (lastVisit != null ? !lastVisit.equals(user.lastVisit) : user.lastVisit != null) return false;
        if (password != null ? !password.equals(user.password) : user.password != null) return false;
        if (roles != null ? !roles.equals(user.roles) : user.roles != null) return false;
        if (signInProvider != user.signInProvider) return false;
        if (tokenConfirmation != null ? !tokenConfirmation.equals(user.tokenConfirmation) : user.tokenConfirmation != null)
            return false;
        if (username != null ? !username.equals(user.username) : user.username != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = id != null ? id.hashCode() : 0;
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (isEnabled ? 1 : 0);
        result = 31 * result + (isConfirmed ? 1 : 0);
        result = 31 * result + (isNonReadOnly ? 1 : 0);
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (roles != null ? roles.hashCode() : 0);
        result = 31 * result + (country != null ? country.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        temp = Double.doubleToLongBits(rating);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + tasksSolved;
        result = 31 * result + (signInProvider != null ? signInProvider.hashCode() : 0);
        result = 31 * result + (tokenConfirmation != null ? tokenConfirmation.hashCode() : 0);
        result = 31 * result + (lastVisit != null ? lastVisit.hashCode() : 0);
        return result;
    }
}
