package dk.cphbusiness.choir.control;

import dk.cphbusiness.choir.contract.dto.ArtistDetail;
import dk.cphbusiness.choir.contract.dto.ArtistSummary;
import dk.cphbusiness.choir.contract.dto.MaterialDetail;
import dk.cphbusiness.choir.contract.dto.MaterialSummary;
import dk.cphbusiness.choir.contract.dto.MemberDetail;
import dk.cphbusiness.choir.contract.dto.MemberSummary;
import dk.cphbusiness.choir.contract.dto.MusicDetail;
import dk.cphbusiness.choir.contract.dto.MusicSummary;
import dk.cphbusiness.choir.contract.dto.RoleSummary;
import dk.cphbusiness.choir.contract.dto.VoiceSummary;
import dk.cphbusiness.choir.model.Artist;
import dk.cphbusiness.choir.model.ChoirMember;
import dk.cphbusiness.choir.model.ChoirRole;
import dk.cphbusiness.choir.model.Material;
import dk.cphbusiness.choir.model.Music;
import dk.cphbusiness.choir.model.Voice;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author Kasper-PC
 */
public class ChoirAssembler {
    
    public static MemberDetail createMemberDetail(ChoirMember member){
        Collection<RoleSummary> roles = new ArrayList<RoleSummary>();
        for(ChoirRole role : member.getChoirRoles()){
            roles.add(new RoleSummary(role.getCode(), role.getName()));
        }
        return new MemberDetail(
                    (long)member.getId(),
                    member.getFirstName(),
                    member.getLastName(),
                    "",
                    false,
                    false,
                    member.getDateOfBirth(),
                    new VoiceSummary(member.getVoice().getCode(),member.getVoice().getName()),
                    roles,
                    member.getStreet(),
                    member.getZipcode(),
                    member.getCity(),
                    member.getEmail(),
                    member.getPhone()
                );
    }
    
    
    //CONTAINS HARDCODED DUMMY VALUES
    public static MaterialDetail createMaterialDetail(Material material){
        Collection<VoiceSummary> voices = new ArrayList<VoiceSummary>();
        Music music = material.getMusic();
        MusicSummary musicSummary = new MusicSummary((long)music.getId(), music.getTitle(), music.getComposer().getPerson().getFirstName(), music.getOpus() + "", "");
        for(Voice voice : material.getVoices()){
            voices.add(new VoiceSummary(voice.getCode(), voice.getName()));
        }

        return new MaterialDetail((long)material.getId(), material.getTitle(), voices, musicSummary, material.getFile(), material.getFileSize(), 0, 0);
    }
    
    //CONTAINS HARDCODED DUMMY VALUES
    public static MusicDetail createMusicDetail(Music music){
        Artist artist = music.getComposer();
        Collection<MaterialSummary> materials = new ArrayList<MaterialSummary>();
        
        ArtistSummary composer = new ArtistSummary(
                artist.getId(),
                artist.getPerson().getFirstName()+" "+artist.getPerson().getLastName(),
                artist.getWikiUrl());
        
        for(Material material : music.getMaterials()){
            materials.add(new MaterialSummary(material.getId(), material.getTitle(), "", "material.getMusic().getDescription()", material.getType()));
        }
        
        MusicDetail musicDetail = new MusicDetail(
                (long)music.getId(),
                music.getTitle(),
                music.getOpus()+"",
                composer,
                materials,
                music.getReleaseYear(),
                music.getPlace(),
                music.getHistory());
        return musicDetail;
    }
    
    public static MemberSummary createMemberSummary(ChoirMember member){
        Format formatter = new SimpleDateFormat("yyyy-MM-dd");
        return new MemberSummary(
                (long)member.getId(),
                member.getVoice().getName(),
                member.getFirstName() + " " + member.getLastName(),
                "",
                member.getStreet() + ", " + member.getZipcode(),
                member.getEmail(),
                member.getPhone(),
                formatter.format(member.getDateOfBirth()),
                true
                );
    }
    
    //CONTAINS HARDCODED DUMMY VALUES
    public static MusicSummary createMusicSummary(Music music){
        return new MusicSummary(music.getId(),
                music.getTitle(),
                music.getComposer().getPerson().getFirstName()+" "+music.getComposer().getPerson().getLastName(),
                music.getOpus()+"", "");
    }
    
    public static ArtistSummary createArtistSummary(Artist artist){
        return new ArtistSummary(artist.getId(), artist.getPerson().getFirstName()+" "+artist.getPerson().getLastName(), 
                artist.getWikiUrl());
    }
    
    public static ArtistDetail createArtistDetail(Artist artist){
        return new ArtistDetail(artist.getId(), artist.getPerson().getFirstName(), artist.getPerson().getLastName(), 
                artist.getWikiUrl(), artist.getCountry(), artist.getPerson().getDateOfBirth(), artist.getDateOfDeath());
    }
    
    
    //CONTAINS HARDCODED DUMMY VALUES
    public static MaterialSummary createMaterialSummary(Material material){
        return new MaterialSummary(material.getId(), material.getTitle(), "", "", material.getType());
    }
    
    
    
    public static VoiceSummary createVoiceSummary(Voice voice)
    {
        return new VoiceSummary(voice.getCode(), voice.getName());
    }
    
    public static RoleSummary createRoleSummary(ChoirRole role)
    {
        return new RoleSummary(role.getCode(), role.getName());
    }

}
