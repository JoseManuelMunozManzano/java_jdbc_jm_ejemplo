package com.jmunoz.bbdd.comics;

import com.jmunoz.bbdd.comics.models.Comic;
import com.jmunoz.bbdd.comics.models.Propietario;
import com.jmunoz.bbdd.comics.models.Tematica;
import com.jmunoz.bbdd.comics.models.Usuario;
import com.jmunoz.bbdd.comics.service.*;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EjemploBDComics {
    public static void main(String[] args) throws SQLException, ParseException {
        // PROBAR USUARIO
        UsuarioService usuarioService = new UsuarioServiceImpl();

        Usuario jose = new Usuario();
        jose.setUsername("jomuma");
        jose.setEmail("neil_mercury@yahoo.es");
        jose.setPassword("hola");

        Usuario adri = new Usuario();
        adri.setUsername("adri");
        adri.setEmail("adriana@yahoo.es");
        adri.setPassword("adios");

        Usuario borrarU = new Usuario();
        borrarU.setUsername("borrar");
        borrarU.setEmail("aborrar@yahoo.es");
        borrarU.setPassword("epa");

        usuarioService.guardar(jose);
        usuarioService.guardar(adri);
        usuarioService.guardar(borrarU);

        usuarioService.listar().forEach(System.out::println);

        borrarU.setPassword("nuevoEpa");
        usuarioService.guardar(borrarU);

        System.out.println(usuarioService.porId(borrarU.getId()));

        usuarioService.eliminar(borrarU.getId());

        usuarioService.listar();

        System.out.println(usuarioService.porUsername("jomuma"));





        // PROBAR TEMATICA
//        TematicaService tematicaService = new TematicaServiceImpl();
//
//        Tematica superheroes = new Tematica();
//        superheroes.setNombre("Superhéroes");
//
//        Tematica guerra = new Tematica();
//        guerra.setNombre("Bélico");
//
//        Tematica scifi = new Tematica();
//        scifi.setNombre("Ciencia Ficción");
//
//        Tematica terror = new Tematica();
//        terror.setNombre("Terror");
//
//        Tematica borrar = new Tematica();
//        borrar.setNombre("A borrar");
//
//        tematicaService.guardar(superheroes);
//        tematicaService.guardar(guerra);
//        tematicaService.guardar(scifi);
//        tematicaService.guardar(terror);
//        tematicaService.guardar(borrar);
//
//        tematicaService.listar().forEach(System.out::println);
//
//        borrar.setNombre("A borrar actualizado");
//        tematicaService.guardar(borrar);
//
//        System.out.println(tematicaService.porId(borrar.getId()));
//
//        tematicaService.eliminar(borrar.getId());
//
//        tematicaService.listar().forEach(System.out::println);

//        System.out.println(tematicaService.porNombre("Superhéroes"));

        Propietario propietario = new Propietario();
        ComicService comicService = new ComicServiceImpl();

        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Comic thor = new Comic();
        Tematica tematica = new Tematica();
        Usuario usuario = new Usuario();

        thor.setNombre("Thor: Renacimiento");
        thor.setPrecio(15F);
        thor.setFechaRegistro(df.parse("20-02-2022"));
        tematica.setNombre("Superhéroes");
        usuario.setUsername("jomuma");

        propietario.setComic(thor);
        propietario.setUsuario(usuario);
        propietario.setTematica(tematica);
        comicService.guardar(propietario);




        Comic maus = new Comic();
        tematica = new Tematica();
        usuario = new Usuario();

        maus.setNombre("Maus");
        maus.setPrecio(22.5F);
        maus.setFechaRegistro(df.parse("10-01-1996"));
        tematica.setNombre("Drama");
        usuario.setUsername("adri");

        propietario.setComic(maus);
        propietario.setUsuario(usuario);
        propietario.setTematica(tematica);
        comicService.guardar(propietario);




        Comic borrar = new Comic();
        tematica = new Tematica();
        usuario = new Usuario();

        borrar.setNombre("20 días");
        borrar.setPrecio(22.5F);
        borrar.setFechaRegistro(df.parse("10-01-1996"));
        tematica.setNombre("Terror");
        usuario.setUsername("jomuma");

        propietario.setComic(borrar);
        propietario.setUsuario(usuario);
        propietario.setTematica(tematica);
        comicService.guardar(propietario);

        comicService.listar().forEach(System.out::println);


        usuario = new Usuario();
        usuario.setUsername("adri");
        propietario.setComic(borrar);
        propietario.setUsuario(usuario);
        propietario.setTematica(tematica);
        Comic nuevoComic = comicService.guardar(propietario);

        System.out.println(comicService.porId(nuevoComic.getId()));

        comicService.eliminar(nuevoComic.getId());

        comicService.listar().forEach(System.out::println);

    }
}
