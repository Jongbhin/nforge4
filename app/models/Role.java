package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * @author "Hwi Ahn"
 */
@Entity
public class Role extends Model {
    private static final long serialVersionUID = 1L;
    private static Finder<Long, Role> find = new Finder<Long, Role>(Long.class,
            Role.class);
    
    public static final Long MANAGER = 1l;
    public static final Long MEMBER = 2l;
    public static final Long SITEMANAGER = 3l;
    public static final Long ANONYMOUS = 4l;
    
    @Id
    public Long id;
    
    public String name;
    public boolean active;
    
    @ManyToMany
    public List<Permission> permissions;
    
    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
    public List<ProjectUser> projectUsers;

    
    public static Role findById(Long id) {
        return find.byId(id);
    }

    public static Role findByName(String name) {
        return find.where().eq("name", name).findUnique();
    }

    /**
     * 프로젝트와 관련된 롤들의 목록을 반환합니다.
     * 
     * @return
     */
    public static List<Role> getActiveRoles() {
        List<Role> projectRoles = find.where().eq("active", true)
                .findList();
        return projectRoles;
    }
    
    /**
     * 해당 유저가 해당 프로젝트에서 가지고 있는 롤을 제공합니다.
     * 
     * @param userId
     * @param projectId
     * @return
     */
    public static Role findRoleByIds(Long userId, Long projectId) {
        return find.where()
                .eq("projectUsers.user.id", userId)
                .eq("projectUsers.project.id", projectId).findUnique();
    }
}
