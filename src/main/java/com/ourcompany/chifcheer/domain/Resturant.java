package com.ourcompany.chifcheer.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;


/**
 * A Resturant.
 */
@Document(collection = "RESTURANT")
public class Resturant implements Serializable {

    @Id
    private String id;

    @NotNull
    @Field("res_name")
    private String resName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Resturant resturant = (Resturant) o;

        if ( ! Objects.equals(id, resturant.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Resturant{" +
                "id=" + id +
                ", resName='" + resName + "'" +
                '}';
    }
}
