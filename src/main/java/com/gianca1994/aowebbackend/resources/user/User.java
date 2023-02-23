package com.gianca1994.aowebbackend.resources.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.gianca1994.aowebbackend.config.ExpLvlConfig;
import com.gianca1994.aowebbackend.config.ModifConfig;
import com.gianca1994.aowebbackend.config.SvConfig;
import com.gianca1994.aowebbackend.resources.classes.Class;
import com.gianca1994.aowebbackend.resources.equipment.Equipment;
import com.gianca1994.aowebbackend.resources.inventory.Inventory;
import com.gianca1994.aowebbackend.resources.item.Item;
import com.gianca1994.aowebbackend.resources.jwt.dto.UserRegisterJwtDTO;
import com.gianca1994.aowebbackend.resources.mail.utilities.AES;
import com.gianca1994.aowebbackend.resources.title.Title;
import com.gianca1994.aowebbackend.resources.user.userRelations.userMail.UserMail;
import com.gianca1994.aowebbackend.resources.user.userRelations.userQuest.UserQuest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.persistence.*;
import java.io.IOException;
import java.io.StringWriter;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * @Author: Gianca1994
 * Explanation: This class is the User entity.
 */

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String email;

    private String role;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_inventory",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "inventory_id",
                    referencedColumnName = "id"))
    private Inventory inventory;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JoinTable(name = "user_equipment",
            joinColumns = @JoinColumn(name = "user_id",
                    referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "equipment_id",
                    referencedColumnName = "id"))
    private Equipment equipment;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<UserQuest> userQuests;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Set<UserMail> userMails;

    @Column
    private String aClass;
    @Column
    private short level;
    @Column
    private Long experience;
    @Column
    private Long experienceToNextLevel;
    @Column
    private long gold;
    @Column
    private int diamond;
    @Column
    private int maxDmg;
    @Column
    private int minDmg;
    @Column
    private int maxHp;
    @Column
    private int hp;
    @Column
    private int defense;
    @Column
    private int evasion;
    @Column
    private float criticalChance;
    @Column
    private int strength;
    @Column
    private int dexterity;
    @Column
    private int intelligence;
    @Column
    private int vitality;
    @Column
    private int luck;
    @Column
    private int freeSkillPoints;
    @Column
    private int npcKills;
    @Column
    private int pvpWins;
    @Column
    private int pvpLosses;
    @Column
    private String titleName;
    @Column
    private int titlePoints;
    @Column
    private String guildName;

    @Column(columnDefinition = "text")
    @JsonIgnore
    private String rsaPublicKey;

    @Column(columnDefinition = "text")
    @JsonIgnore
    private String rsaPrivateKey;

    public User(UserRegisterJwtDTO userJwt) {
        this.username = userJwt.getUsername();
        this.password = userJwt.getPassword();
        this.email = userJwt.getEmail();
        this.role = SvConfig.STANDARD_ROLE;
        this.inventory = userJwt.getInventory();
        this.equipment = userJwt.getEquipment();
        this.aClass = userJwt.getAClass().getName();
        this.level = ModifConfig.START_LVL;
        this.experience = ModifConfig.START_EXP;
        this.experienceToNextLevel = ExpLvlConfig.getExpInitial();
        this.gold = ModifConfig.START_GOLD;
        this.diamond = ModifConfig.START_DIAMOND;
        this.maxDmg = 0;
        this.minDmg = 0;
        this.maxHp = 0;
        this.hp = 0;
        this.strength = userJwt.getAClass().getStrength();
        this.dexterity = userJwt.getAClass().getDexterity();
        this.intelligence = userJwt.getAClass().getIntelligence();
        this.vitality = userJwt.getAClass().getVitality();
        this.luck = userJwt.getAClass().getLuck();
        this.freeSkillPoints = ModifConfig.START_FREE_SKILL_POINTS;
        this.npcKills = 0;
        this.pvpWins = 0;
        this.pvpLosses = 0;
        this.titleName = ModifConfig.TITLES.get(0).getName();
        this.titlePoints = 0;
        this.guildName = "";
    }

    public void generatePrivateAndPublicKey() throws InterruptedException {
        /**
         * @Author: Gianca1994
         * Explanation: This method generates a private and public key for the user in PEM format.
         * @return none
         */
        AES aes = new AES();
        Thread keyGeneratorThread = new Thread(() -> {
            try {
                KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
                keyPairGenerator.initialize(2048);
                KeyPair keyPair = keyPairGenerator.generateKeyPair();
                RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
                RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

                StringWriter stringWriter = new StringWriter();
                PemWriter pemWriter = new PemWriter(stringWriter);

                PemObject pemObject = new PemObject("PUBLIC KEY", publicKey.getEncoded());
                pemWriter.writeObject(pemObject);
                pemWriter.flush();
                String publicKeyString = stringWriter.toString();

                pemObject = new PemObject("PRIVATE KEY", privateKey.getEncoded());
                stringWriter = new StringWriter();
                pemWriter = new PemWriter(stringWriter);
                pemWriter.writeObject(pemObject);
                pemWriter.flush();
                String privateKeyString = stringWriter.toString();

                this.rsaPublicKey = publicKeyString;
                this.rsaPrivateKey = aes.encryptMsg(privateKeyString);

                pemWriter.close();
            } catch (NoSuchAlgorithmException | IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        keyGeneratorThread.start();
        keyGeneratorThread.join();
    }


    //********** START SWAP ITEM METHODS **********//
    public void swapItemToEquipmentOrInventory(Item item, boolean toEquip) {
        /**
         * @Author: Gianca1994
         * Explanation: This method swaps an item from the inventory to the equipment or vice versa.
         * @param Item item
         * @param boolean toEquip
         * @return none
         */
        int multiplier = toEquip ? 1 : -1;

        this.strength += item.getStrength() * multiplier;
        this.dexterity += item.getDexterity() * multiplier;
        this.intelligence += item.getIntelligence() * multiplier;
        this.vitality += item.getVitality() * multiplier;
        this.luck += item.getLuck() * multiplier;

        calculateStats(false);
    }
    //********** END SWAP ITEM METHODS **********//

    //********** START TITLE UPDATE METHODS **********//
    public void updateTitle() {
        /**
         * @Author: Gianca1994
         * Explanation: Checks if the user has enough points to change title
         * @param none
         * @return none
         */
        Title currentTitle = ModifConfig.TITLES.stream().filter(title -> title.getName().equals(this.titleName)).findFirst().orElse(null);
        if (currentTitle == null) return;

        List<Title> filteredTitles = ModifConfig.TITLES.stream().filter(title -> title.getMinPts() <= this.titlePoints && title.getMinLvl() <= this.level).collect(Collectors.toList());
        Title newTitle = filteredTitles.stream().reduce((t1, t2) -> t1.getMinPts() > t2.getMinPts() ? t1 : t2).orElse(currentTitle);
        if (currentTitle.equals(newTitle)) return;

        this.strength += newTitle.getStrength() - currentTitle.getStrength();
        this.dexterity += newTitle.getDexterity() - currentTitle.getDexterity();
        this.intelligence += newTitle.getIntelligence() - currentTitle.getIntelligence();
        this.vitality += newTitle.getVitality() - currentTitle.getVitality();
        this.luck += newTitle.getLuck() - currentTitle.getLuck();
        this.titleName = newTitle.getName();
        calculateStats(false);
    }

    public void addTitlePoints(int amount) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to add title points to the user.
         * @param int amount
         * @return none
         */
        this.titlePoints += amount;
        updateTitle();
    }

    public void removeTitlePoints(int amount) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to remove title points from the user.
         * @param int amount
         * @return none
         */
        if (this.titlePoints >= amount) this.titlePoints -= amount;
        else this.titlePoints = 0;
        updateTitle();
    }
    //********** END TITLE UPDATE METHODS **********//

    //********** START CHECK LEVEL UP **********//
    public boolean userLevelUp() {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to level up the user.
         * @return boolean
         */
        boolean userLevelUp = false;
        boolean levelUp;
        do {
            if (this.level < SvConfig.LEVEL_MAX && this.experience >= this.experienceToNextLevel) {
                levelUp = true;
                userLevelUp = true;
                this.freeSkillPoints += ModifConfig.FREE_SKILL_POINTS_PER_LEVEL;
                this.experience -= this.experienceToNextLevel;
                this.experienceToNextLevel = ExpLvlConfig.getExpNextLevel(this.level);
                this.level++;
            } else levelUp = false;
        } while (levelUp);
        if (this.level >= SvConfig.LEVEL_MAX) {
            this.experience = 0L;
            this.experienceToNextLevel = 0L;
        }
        return userLevelUp;
    }
    //********** END CHECK LEVEL UP **********//

    //********** START CALCULATE STATS **********//
    public void calculateStats(boolean fullMinHp) {
        /**
         * @Author: Gianca1994
         * Explanation: Calculates the stats of the user
         * @param boolean fullMinHp
         * @return void
         */
        Class aClass = ModifConfig.CLASSES.stream().filter(c -> c.getName().equals(this.getAClass())).findFirst().orElse(null);
        if (aClass == null) return;

        if (aClass.getName().equals("mage")) setStats(this.intelligence, this.strength, this.dexterity);
        else if (aClass.getName().equals("warrior")) setStats(this.strength, this.intelligence, this.dexterity);
        else if (aClass.getName().equals("archer")) setStats(this.dexterity, this.intelligence, this.strength);

        applyModifiers(aClass);
        if (fullMinHp) this.hp = this.maxHp;
    }

    private void setStats(int minMaxDmg, int defense, int evasion) {
        /**
         * @Author: Gianca1994
         * Explanation: Sets the stats of the user
         * @param int minMaxDmg
         * @param int defense
         * @param int evasion
         * @return void
         */
        this.minDmg = minMaxDmg;
        this.maxDmg = minMaxDmg;
        this.defense = defense;
        this.evasion = evasion;
    }

    private void applyModifiers(Class aClass) {
        /**
         * @Author: Gianca1994
         * Explanation: Applies the modifiers of the class to the stats of the user
         * @param Class aClass
         * @return void
         */
        this.minDmg *= aClass.getMinDmgModifier();
        this.maxDmg *= aClass.getMaxDmgModifier();
        this.defense *= aClass.getDefenseModifier();
        this.evasion *= aClass.getEvasionModifier();
        this.maxHp = this.vitality * aClass.getMaxHpModifier();
        this.criticalChance = Math.min(this.luck * aClass.getCriticalModifier(), ModifConfig.MAX_CRITICAL_PERCENTAGE);
    }
    //********** END CALCULATE STATS **********//

    //************ CALCULATE GUILD CREATE ************//
    public void userCreateGuild(String guildName, long goldCost, int diamondsCost) {
        /**
         * @Author: Gianca1994
         * Explanation: This method is used to create a guild
         * @param String guildName
         * @param long goldCost
         * @param int diamondsCost
         * @return void
         */
        this.setGuildName(guildName);
        this.setGold(this.getGold() - goldCost);
        this.setDiamond(this.getDiamond() - diamondsCost);
    }
    //********** END CALCULATE GUILD CREATE **********//
}