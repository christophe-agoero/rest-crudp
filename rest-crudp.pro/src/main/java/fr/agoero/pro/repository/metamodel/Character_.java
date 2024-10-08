package fr.agoero.pro.repository.metamodel;

import fr.agoero.pro.domain.Character;
import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Character.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Character_ {

    public static final String FIRSTNAME = "firstname";
    public static final String CODE = "code";
    public static final String APPEARANCE = "appearance";
    public static final String ID = "id";
    public static final String LASTNAME = "lastname";


    /**
     * @see Character#firstname
     **/
    public static volatile SingularAttribute<Character, String> firstname;

    /**
     * @see Character#code
     **/
    public static volatile SingularAttribute<Character, String> code;

    /**
     * @see Character#appearance
     **/
    public static volatile SingularAttribute<Character, Integer> appearance;

    /**
     * @see Character#id
     **/
    public static volatile SingularAttribute<Character, Integer> id;

    /**
     * @see Character
     **/
    public static volatile EntityType<Character> class_;

    /**
     * @see Character#lastname
     **/
    public static volatile SingularAttribute<Character, String> lastname;

}

