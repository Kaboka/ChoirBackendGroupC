package dk.cphbusiness.choir.control;

import dk.cphbusiness.choir.contract.ChoirManager;
import dk.cphbusiness.choir.contract.dto.ArtistDetail;
import dk.cphbusiness.choir.contract.dto.ArtistSummary;
import dk.cphbusiness.choir.contract.dto.MaterialDetail;
import dk.cphbusiness.choir.contract.dto.MaterialSummary;
import dk.cphbusiness.choir.contract.dto.MemberAuthentication;
import dk.cphbusiness.choir.contract.dto.MemberDetail;
import dk.cphbusiness.choir.contract.dto.MemberSummary;
import dk.cphbusiness.choir.contract.dto.MusicDetail;
import dk.cphbusiness.choir.contract.dto.MusicSummary;
import dk.cphbusiness.choir.contract.dto.RoleSummary;
import dk.cphbusiness.choir.contract.dto.VoiceSummary;
import dk.cphbusiness.choir.contract.eto.AuthenticationException;
import dk.cphbusiness.choir.contract.eto.NoSuchArtistException;
import dk.cphbusiness.choir.contract.eto.NoSuchMaterialException;
import dk.cphbusiness.choir.contract.eto.NoSuchMemberException;
import dk.cphbusiness.choir.contract.eto.NoSuchMusicException;
import dk.cphbusiness.choir.model.Artist;
import dk.cphbusiness.choir.model.Audio;
import dk.cphbusiness.choir.model.ChoirMember;
import dk.cphbusiness.choir.model.ChoirRole;
import dk.cphbusiness.choir.model.Material;
import dk.cphbusiness.choir.model.Music;
import dk.cphbusiness.choir.model.Sheet;
import dk.cphbusiness.choir.model.Voice;
import java.util.ArrayList;
import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NamedQuery;
import javax.persistence.Persistence;
import javax.persistence.Query;


/**
 *
 * @author
 * kasper
 */
@Stateless
public class ChoirManagerBean implements ChoirManager{

    @Override
    public MemberAuthentication login(String email, String password) throws AuthenticationException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        ChoirMember member = null;
        try{
        member = (ChoirMember)em.createQuery("SELECT m FROM ChoirMember m WHERE m.email= :email AND m.password=:password")
                                                             .setParameter("email", email)
                                                             .setParameter("password", password)
                                                             .getSingleResult();
        }catch(Exception e){
         throw new AuthenticationException("failed login");
        }
        MemberAuthentication authentication = new MemberAuthentication(member.getId(),member.getEmail() );
        for(ChoirRole role : member.getChoirRoles()){
            authentication.addRoleCode(role.getCode());
        }
        
        return authentication;
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void changePassword(String oldPassword, String newPassword) throws AuthenticationException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        
        //Which user is logged in? I have no MemberAuthentication in my parameters to know who's logged in.
    }

    @Override
    public Collection<VoiceSummary> listVoices() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<VoiceSummary> voices = new ArrayList<VoiceSummary>();
        Collection<Voice> list = em.createNamedQuery("Voice.findAll").getResultList();
        for(Voice voice : list)
        {
            System.out.println(voice.getName());
            voices.add(ChoirAssembler.createVoiceSummary(voice));
            
        }
        return voices;
    }

    @Override
    public Collection<RoleSummary> listRoles() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<RoleSummary> roles = new ArrayList<RoleSummary>();
        Collection<ChoirRole> list = em.createNamedQuery("ChoirRole.findAll").getResultList();
        for(ChoirRole role : list)
        {
            roles.add(ChoirAssembler.createRoleSummary(role));
        }
        return roles;
    }

    @Override
    public Collection<MemberSummary> listMembers() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<MemberSummary> members = new ArrayList<MemberSummary>();
        Collection<ChoirMember> list = em.createNamedQuery("ChoirMember.findAll").getResultList();
        for(ChoirMember member : list)
        {
            members.add(ChoirAssembler.createMemberSummary(member));
        }
        return members;
    }

    @Override
    public Collection<MemberSummary> listMembersByRole(String roleCode) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        ChoirRole role = em.find(ChoirRole.class, roleCode);
                System.out.println(role.getCode());
        Collection<MemberSummary> members = new ArrayList<MemberSummary>();
        for(ChoirMember member : role.getMembers()){
            members.add(ChoirAssembler.createMemberSummary(member));
        }
        
        return members;
    }

    @Override
    public Collection<MemberSummary> listMembersByVoices(int voiceCodes) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Voice voice = em.find(Voice.class,voiceCodes);
        Collection<MemberSummary> members = new ArrayList<MemberSummary>();
        for(ChoirMember member : voice.getMembers()){
            members.add(ChoirAssembler.createMemberSummary(member));
        }
        return members;
    }

    @Override
    public MemberDetail findMember(long id) throws NoSuchMemberException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        MemberDetail member = ChoirAssembler.createMemberDetail(em.find(ChoirMember.class, (int)id));
        return member;  
    }

    @Override
    public MemberDetail saveMember(MemberAuthentication user, MemberDetail member) throws NoSuchMemberException, AuthenticationException {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        
        //Checks if user has permission to edit this member. Only allowed if user is admin or editing themselves.
        if(checkAdmin(user)){
            em.getTransaction().begin();
            ChoirMember choirMember = new ChoirMember();
            if(member.getId()!= 0){
               choirMember.setId((int)member.getId());
            }
            choirMember.setFirstName(member.getFirstName());
            choirMember.setLastName(member.getLastName());
            choirMember.setDateOfBirth(member.getDateOfBirth());
            Collection<ChoirRole> roles = new ArrayList<ChoirRole>();
            for(String roleCode : member.getRoleCodes()){
                roles.add(em.find(ChoirRole.class, roleCode));
            }
            choirMember.setChoirRoles(roles);
            choirMember.setVoice(em.find(Voice.class, member.getVoiceCode()));
            choirMember.setStreet(member.getStreet());
            choirMember.setZipcode(member.getZipCode());
            choirMember.setCity(member.getCity());
            choirMember.setEmail(member.getEmail());
            choirMember.setPhone(member.getPhone());
            
            
            if(choirMember.getId() == null){
                em.persist(choirMember);            //Creates new member if it doesn't already exist in DB
            }   
            else{
                choirMember.setPassword(em.find(ChoirMember.class, (int)member.getId()).getPassword());
                em.merge(choirMember);            //Updates if member already exists in DB
            }         
                 
            em.getTransaction().commit();
            em.close();
        }
        else{
            throw new AuthenticationException("User has insufficient rights.");
        }
        
        return member;
    }

    @Override
    public Collection<MaterialSummary> listMaterials() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        
        Collection<MaterialSummary> materialSummaries = new ArrayList<MaterialSummary>();
//        ArrayList<Material> materials = new ArrayList<Material>(em.createNamedQuery("Material.findAll").getResultList());
//        System.out.println("Materials: " + materials.toString());
        ArrayList<Sheet> sheets = new ArrayList<Sheet>(em.createNamedQuery("Sheet.findAll").getResultList());
        System.out.println("Sheets: " + sheets.toString());
        ArrayList<Audio> audios = new ArrayList<Audio>(em.createNamedQuery("Audio.findAll").getResultList());
        System.out.println("Audios: " + audios.toString());
        for(Sheet sheet : sheets){
            materialSummaries.add(ChoirAssembler.createMaterialSummary(sheet));
        }
        for(Audio audio : audios){
            materialSummaries.add(ChoirAssembler.createMaterialSummary(audio));
        }
        System.out.println("Material summaries: " + materialSummaries.toString());
        return materialSummaries;
    }

    @Override
    public Collection<MaterialSummary> listMaterialsByVoices(int voiceCodes) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<MaterialSummary> materials = new ArrayList<MaterialSummary>();
        Voice voice = em.find(Voice.class,voiceCodes);
        for(Material material : voice.getMaterials()){
            materials.add(ChoirAssembler.createMaterialSummary(material));
        }
        
        return materials;
    }

    @Override
    public Collection<MaterialSummary> listMaterialsByMusic(long musicId) throws NoSuchMusicException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<MaterialSummary> materials = new ArrayList<MaterialSummary>();
        Music music = em.find(Music.class,(int)musicId);
        for(Material material : music.getMaterials()){
            materials.add(ChoirAssembler.createMaterialSummary(material));
        }
        
        return materials;
    }

    @Override
    public MaterialDetail findMaterial(long id) throws NoSuchMaterialException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        MaterialDetail material = ChoirAssembler.createMaterialDetail(em.find(Material.class, (int)id));
        
        return material;
    }

    @Override
    public MaterialDetail saveMaterial(MemberAuthentication user, MaterialDetail material) throws NoSuchMaterialException, AuthenticationException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
//        Material cMaterial = new Material(material.getId()); --> abstract, cannot use
        
        //TODO: use the type variable in MaterialDetail to determine wether it's a sheet or an audio file, so we can persist the Material Object.
        
//        em.getTransaction().begin();
//        
//        for(VoiceSummary voiceSummary : material.getVoices()){
//                Voice voice = new Voice(voiceSummary.getCode());
//                voice.setName(voiceSummary.getName());
//                cMaterial.getVoices().add(voice);
//        }
//            
//            if(cMaterial.getId() == 0){
//                em.persist(cMaterial);            //Creates new material if it doesn't already exist in DB
//            }   
//            else{
//                em.refresh(cMaterial);            //Updates if material already exists in DB
//            }         
//                 
//            em.getTransaction().commit();
//            em.close();
        return material;
    }

    @Override
    public Collection<MusicSummary> listMusic() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<MusicSummary> musicList = new ArrayList<MusicSummary>();
        Collection<Music> list = em.createNamedQuery("Music.findAll").getResultList();
        for(Music music : list)
        {          
            musicList.add(ChoirAssembler.createMusicSummary(music));
        }
        
        return musicList;
    }

    @Override
    public Collection<MusicSummary> listMusicByComposer(long composerId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<MusicSummary> musicList = new ArrayList<MusicSummary>();
        Artist composer = em.find(Artist.class, (int)composerId);
        for(Music music : composer.getMusic()){
            musicList.add(ChoirAssembler.createMusicSummary(music));
        }
        
        return musicList;
    }

    @Override
    public MusicDetail findMusic(long id) throws NoSuchMusicException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        MusicDetail music = ChoirAssembler.createMusicDetail(em.find(Music.class, (int)id));
        return music;
    }

    @Override
    public MusicDetail saveMusic(MemberAuthentication user, MusicDetail music) throws NoSuchMusicException, AuthenticationException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        
        
            
            em.getTransaction().begin();
            
            
            Artist artist = new Artist();
            artist.setId((int)music.getComposer().getId());
            artist.setWikiUrl(music.getComposer().getWikiUrl());
            
//          artist.setCountry(music.getComposer().getCountry());                MISSING GETCOUNTRY METHOD
//          artist.setDateOfDeath(music.getComposer().getDateOfDeath());        MISSING GETDATEOFDEATH METHOD
//          artist.setMusic(music.getComposer().getMusic());                    MISSING GETMUSIC METHOD
//          artist.setPerson(music.getComposer().getPerson());                  MISSING GETPERSON METHOD
            
            Collection<Material> materials = new ArrayList<Material>();
            
            
            
            Music cMusic = new Music();
            cMusic.setId((int)music.getId());
            cMusic.setTitle(music.getTitle());
            cMusic.setHistory(music.getHistory());
            cMusic.setComposer(artist);
//            cMusic.setMaterials(music.getMaterials());
//            
//            for(RoleSummary role : member.getRoles()){
//                ChoirRole cRole = new ChoirRole(role.getCode());
//                cRole.setName(role.getName());
//                choirMember.getChoirRoles().add(cRole);
//            }
//            
//            if(choirMember.getId() == 0){
//                em.persist(choirMember);            //Creates new member if it doesn't already exist in DB
//            }   
//            else{
//                em.refresh(choirMember);            //Updates if member already exists in DB
//            }         
                 
            em.getTransaction().commit();
            em.close();
        
        return music;
    }

    @Override
    public Collection<ArtistSummary> listArtists() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Collection<ArtistSummary> artistList = new ArrayList<ArtistSummary>();
        Collection<Artist> list = em.createNamedQuery("Artist.findAll").getResultList();
        for(Artist artist : list)
        {
            artistList.add(ChoirAssembler.createArtistSummary(artist));
        }
        
        return artistList;
    }

    @Override
    public Collection<ArtistSummary> listArtistsByPattern(String term) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery("Artist.findPattern");
        query.setParameter("term", "%"+term+"%");
        Collection<Artist> artists = query.getResultList();
        Collection<ArtistSummary> list = new ArrayList<ArtistSummary>();
        for(Artist a : artists){
            list.add(ChoirAssembler.createArtistSummary(a));
        }
        return list;
    }

    @Override
    public ArtistDetail findArtist(long id) throws NoSuchArtistException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        ArtistDetail artist = ChoirAssembler.createArtistDetail(em.find(Artist.class, (int)id));
        
        return artist;
    }
    
    private boolean checkAdmin(MemberAuthentication user){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        ChoirMember u = em.find(ChoirMember.class, (int)user.getId());
        boolean isAdmin = false;
        for(ChoirRole r : u.getChoirRoles()){
            if(r.getCode().equals("ADM")){
                isAdmin = true;
            }
        }
        return isAdmin;
    }

    @Override
    public ArtistDetail saveArtist(MemberAuthentication user, ArtistDetail artist) throws NoSuchArtistException, AuthenticationException {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("ChoirBackendPU");
        EntityManager em = emf.createEntityManager();
        
        if(checkAdmin(user)){
            em.getTransaction().begin();
    
            
            Artist saveArtist = new Artist();
            
//            saveArtist.setFirstName(artist.getFirstName());
//            saveArtist.setLastName(artist.getLastName());
//            saveArtist.setWikiUrl(artist.getWikiUrl());
//            saveArtist.setCountry(artist.getCountry());
//            saveArtist.setDateOfBirth(artist.getDateOfBirth());
//            saveArtist.setDateOfDeath(artist.getDateOfDeath());
            
            
            
            if(artist.getId() == 0){
                em.persist(saveArtist);            //Creates new member if it doesn't already exist in DB
            }   
            else{
                em.refresh(saveArtist);            //Updates if member already exists in DB
            }         
                 
            em.getTransaction().commit();
            em.close();
        }
        else{
            throw new AuthenticationException("User has insufficient rights.");
        }
        
        return artist;
    }
    }
