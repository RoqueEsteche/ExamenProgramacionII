# Examen Final - Programación II: App de Gestión Académica

## Descripción del Sistema

Esta es una aplicación para Android desarrollada en Java que permite la gestión de entidades académicas. La aplicación está diseñada siguiendo las mejores prácticas de arquitectura de Android, utilizando componentes como Room, ViewModel, LiveData, Navigation Component y ViewBinding.

### Funcionalidades Principales

*   **Gestión de Estudiantes (CRUD Completo):**
    *   Listar todos los estudiantes en la pantalla principal.
    *   Añadir un nuevo estudiante a través de un formulario.
    *   Editar los datos de un estudiante existente haciendo clic sobre él en la lista.
    *   Eliminar un estudiante de la lista deslizándolo hacia la izquierda o derecha.

*   **Gestión de Materias (CRUD Parcial):
    *   Listar todas las materias en su pantalla correspondiente.
    *   Añadir una nueva materia a través de un diálogo emergente (pop-up).

*   **Navegación:**
    *   La aplicación cuenta con una barra de navegación inferior (`BottomNavigationView`) para cambiar entre las secciones principales (Estudiantes y Materias).
    *   Una barra de herramientas superior (`Toolbar`) muestra el título de la pantalla actual y botones de navegación contextuales.

*   **Persistencia de Datos:**
    *   Toda la información se almacena localmente en el dispositivo utilizando una base de datos SQLite, gestionada a través de la biblioteca de persistencia Room.
    *   Se han definido 4 entidades relacionadas: `Estudiante`, `Materia`, `Inscripcion` y `Calificacion`, demostrando el uso de llaves foráneas (`@ForeignKey`).

## Arquitectura y Tecnologías

*   **Lenguaje:** Java
*   **Arquitectura:** MVVM (Model-View-ViewModel)
*   **Base de Datos:** Room sobre SQLite
*   **Componentes de Android Jetpack:**
    *   **ViewBinding:** Para una interacción segura y eficiente con las vistas.
    *   **ViewModel:** Para gestionar la lógica de la UI y sobrevivir a cambios de configuración.
    *   **LiveData:** Para observar cambios en los datos y actualizar la UI de forma reactiva.
    *   **Navigation Component:** Para gestionar la navegación entre las diferentes pantallas (fragmentos) de la aplicación.
*   **Interfaz de Usuario:**
    *   Uso de `RecyclerView` y `ListAdapter` para mostrar listas de datos de manera eficiente.
    *   Uso de `Material Design Components` para elementos como `Toolbar`, `BottomNavigationView`, `FloatingActionButton`, y `TextInputLayout`.
