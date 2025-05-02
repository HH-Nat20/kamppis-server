<a id="alku"></a>

# _Kämppis_ ja SOLID

<details>
<summary><b>Sisällysluetto</b></summary>
  <ul>
    <li>
        <a href="#johdanto">Johdanto</a>
    </li>
    <li>
        <a href="#solid-periaatteet">SOLID-periaatteet</a>
    </li>
    <ul>
        <li>
            <a href="#1-single-responsibility-principle">1. Single Responsibility Principle</a>
        </li>
        <li>
            <a href="#2-open-closed-principle">2. Open-Closed Principle</a>
        </li>
        <li>
            <a href="#3-liskov-substitution-principle">3. Liskov Substitution Principle</a>
        </li>
        <li>
            <a href="#4-interface-segregation-principle">4. Interface Segregation Principle</a>
        </li>
        <li>
            <a href="#5-dependency-inversion-principle">5. Dependency Inversion Principle</a>
        </li>
    </ul>
    <li>
        <a href="#miten-solid-periaatteet-toteutuvat-kämppiksessä">Miten SOLID-periaatteet toteutuvat <i>Kämppiksessä</i>?</a>
    </li>
    <ul>
        <li>
            <a href="#hyviä-esimerkkejä-solid-periaatteista-kämppiksessä">Hyviä esimerkkejä SOLID-periaatteista <i>Kämppiksessä</i></a>
        </li>
        <ul>
            <li>
                <a href="#single-responsibility-principle">Single Responsibility Principle</a>
            </li>
                <li>
                <a href="#liskov-substitution-principle">Liskov Substitution Principle</a>
            </li>
            <li>
                <a href="#interface-segregation-principle">Interface Segregation Principle</a>
            </li>
            <li>
                <a href="#dependency-inversion-principle">Dependency Inversion Principle</a>
            </li>
        </ul>
        <li>
            <a href="#parannusehdotuksia-solid-periaatteiden-toteutumiseksi-kämppiksessä">Parannusehdotuksia SOLID-periaatteiden toteutumiseksi <i>Kämppiksessä</i></a>
        </li>
        <ul>
            <li>
                 <a href="#single-responsibility-principle-1">Single Responsibility Principle</a>
            </li>
            <li>
                <a href="#open-closed-principle">Open Closed Principle</a>
            </li>
        </ul>
    </ul>
    <li>
        <a href="#lopuksi">Lopuksi</a>
    </li>
    <li>
        <a href="#lähteet">Lähteet</a>
    </li>
  </ul>
</details>

## Johdanto

Tämä dokumentti kuvaa [Ohjelmistoprojekti 2](https://opinto-opas.haaga-helia.fi/course_unit/SOF007AS3A) -kurssilla toteutetun [_Kämppis_](https://github.com/HH-Nat20)-sovelluksen back-endin lähdekoodin toteutusta sellaisena kuin se oli tarkasteluhetkellä (2.5.2025) ja pohtii, miten serveripuolen koodia voisi parantaa SOLID-periaatteiden mukaisesti.

Back-end on rakennettu [Kotlinilla](https://kotlinlang.org/) käyttäen [Spring Boot](https://spring.io/projects/spring-boot) -frameworkia. Koodiblokit, niin teoriaesimerkit, otteet lähdekoodista sekä lähdekoodin parannusehdotukset, on kirjoitettu Kotlinilla. Tämä tarkastelu keskittyy _Kämppis_-sovelluksen back-endiin ([repositorio GitHubissa](https://github.com/HH-Nat20/kamppis-server)) sen sijaan, että tarkastelussa otettaisiin huomioon myös sovelluksen front-end eli _Kämppiksen_ mobiilisovellus. Mobiilisovelluksesta kiinnostuneet voivat tutustua sen [repositorioon GitHubissa](https://github.com/HH-Nat20/kamppis-app).

Tarkastelun aluksi tutustumme SOLID-periaatteisiin, minkä jälkeen analysoimme _Kämppiksen_ back-endin lähdekoodia ja etsimme sekä hyviä että parannettavia esimerkkejä SOLID-periaatteista.

<p align="right">(<a href="#alku">Takaisin alkuun</a>)</p>

## SOLID-periaatteet

SOLID on kehittäjä Robert C. Martiniin yhdistettävä kirjainyhdistelmä, joka kokoaa viisi ohjelmistoarkkitehtuurille ja -suunnittelulle tärkeää periaatetta. Noudattamalla SOLID-periaatteita ohjelmisto osat rakennetaan niin, että ohjelmisto on helposti ymmärrettävissä ja joustavasti ylläpidettävissä. Tarkastelun teoriapohjana on käytetty Martinin teosta _Clean architecture: A Craftsman’s Guide to Software Structure and Design_ (Martin 2018) sekä Baeldungin artikkelia _A Solid Guide to SOLID Principles_ (Millington 2025).

SOLID-periaatteisiin kuuluvat alla esitellyt viisi periaatetta: [Single Responsibility Principle](#1-single-responsibility-principle), [Open-Closed Principle](#2-open-closed-principle), [Liskov Substitution Principle](#3-liskov-substitution-principle), [Interface Segregation Principle](#4-interface-segregation-principle) ja [Dependency Inversion Principle](#5-dependency-inversion-principle).

### 1. Single Responsibility Principle

---

_Single Responsibility Principle_ tarkoittaa yhden vastuun periaatetta. Tämän periaatteen mukaan luokan tulisi vastata vain yhdestä tehtävästä tai vastuusta.

Esimerkiksi käyttäjän autentikoinnista vastaavan luokan ei tulisi samalla hoitaa tietokantatransaktioita (alla `UserManager`), vaan nämä kaksi toimintaa tulisi jakaa kahdeksi omaksi luokakseen (alla `UserRepository` ja `AuthenticationService`).

<details>
<summary>❌ Ei näin</summary>

```kotlin
class UserManager {
    fun saveUser(user: User) {
        println("Käyttäjä tallennettu tietokantaan!")
    }

    fun authenticateUser(user: User) {
        println("Käyttäjä tunnistettu onnistuneesti!")
    }
}
```

</details>

<details>
<summary>✅ Vaan näin</summary>

```kotlin
class UserRepository {
    fun save(user: User) {
        println("Käyttäjä tallennettu tietokantaan!")
    }
}

class AuthenticationService {
    fun authenticateUser(user: User) {
        println("Käyttäjä tunnistettu onnistuneesti!")
    }
}
```

</details>

---

### 2. Open-Closed Principle

---

_Open-Closed Principle_ tarkoittaa avoimuuden ja sulkeutuneisuuden periaatetta. Tämän periaatteen mukaisesti ohjelman komponenttien (kuten luokkien ja funktioiden) tulisi olla avoimia laajennuksille, mutta suljettuja muutoksille. Toisin sanoen, koodiin tulisi voida lisätä uutta toiminnallisuutta muuttamatta olemassa olevaa koodia.

Esimerkiksi alennuksen laskevan komponentin A tulisi laskea alennushinta niin, että komponentteja E ja O voidaan käyttää laskemaan alennushinta eläkeläisille ja opiskelijoille muuttamatta komponentin A koodia. Myöhemmin voidaan lisätä esimerkiksi komponentti V varusmiehille edelleen muuttamatta komponenttia A.

Alla esimerkissä `DiscountCalculator`-luokka voi käyttää mitä tahansa `Discount`-rajapintaa toteuttavaa luokkaa, kuten `StudentDiscount` ja `SeniorDiscount`. `DiscountCalculator` voi muuttumattomana hyödyntää jotakin myöhemmin lisättävää luokkaa, esimerkiksi `ConscriptDiscount`, jos se toteuttaa `Discount`-rajapintaa.

<details>
<summary>❌ Ei näin</summary>

```kotlin
class DiscountCalculator {
    fun calculate(type: String, price: Double): Double {
        return when (type) {
            "Student" -> price * 0.9
            "Senior" -> price * 0.85
            // Tähän lisättävä uusia alennusryhmiä eli muokattava luokkaa
            else -> price
        }
    }
}
```

</details>

<details>
<summary>✅ Vaan näin</summary>

```kotlin
interface Discount {
    fun apply(price: Double): Double
}

class StudentDiscount : Discount {
    override fun apply(price: Double) = price * 0.9
}

class SeniorDiscount : Discount {
    override fun apply(price: Double) = price * 0.85
}

class DiscountCalculator {
    fun calculate(discount: Discount, price: Double): Double {
        return discount.apply(price)
    }
}
```

</details>

---

### 3. Liskov Substitution Principle

---

_Liskov Substitution Principle_ tarkoittaa (Barbara) Liskovin korvaavuusperiaatetta. Tämän periaatteen mukaan aliluokkien pitää voida korvata yläluokka ilman ongelmia.

Esimerkiksi jos luokka `Cat` on luokan `Animal` aliluokka, `Cat`-luokan pitäisi voida turvallisesti korvata `Animal`-luokka missä kohtaa ohjelmaa tahansa ilman että ohjelman toiminta muuttuu.

<details>
<summary>❌ Ei näin</summary>

```kotlin
open class Animal {
    open fun makeSound() {
        println("Eläin tekee äänen")
    }
}

class Cat : Animal() {
    override fun makeSound() {
        throw UnsupportedOperationException("Kissat eivät tee ääntä tällä tavalla!")
    }
}

fun playSound(animal: Animal) {
    animal.makeSound()
}
```

```kotlin
val myCat = Cat()
playSound(myCat) // Heittää poikkeuksen!
```

</details>

<details>
<summary>✅ Vaan näin</summary>

```kotlin
open class Animal {
    open fun makeSound() {
        println("Tuntematon eläinääni")
    }
}

class Cat : Animal() {
    override fun makeSound() {
        println("Miau")
    }
}

class Dog : Animal() {
    override fun makeSound() {
        println("Hau hau")
    }
}

fun playSound(animal: Animal) {
    animal.makeSound()
}
```

```kotlin
val cat = Cat()
val dog = Dog()

playSound(cat) // Print: Miau
playSound(dog) // Print: Hau hau
```

</details>

---

### 4. Interface Segregation Principle

---

_Interface Segregation Principle_ tarkoittaa rajapintojen erottelun periaatetta. Tämän periaatteen mukaan luokkia ei tulisi pakottaa toteuttamaan rajapintoja, joita ko. luokat eivät käytä. Periaatteen mukaan olisi parempi tehdä monta pientä ja tarkasti määriteltyä rajapintaa kuin yksi suuri ja yleiskäyttöinen rajapinta, jotta luokat eivät joudu toteuttamaan turhia metodeja rajapintojen kautta.

Esimerkiksi `Robot`-luokan ei tulisi implementoida rajapintaa, jonka sopimukseen kuuluu `work()`- ja `eat()`-metodit, sillä robotit eivät syö (paitsi ehkä muttereita 🤖). Sen sijaan tulisi laatia kaksi rajapintaa, jotka tarjoavat `work()`- ja `eat()`-metodit erikseen, jotta `Robot`- ja `Human`-luokat voivat tarkemmin määritellä, mitä metodeja niiden tulee pystyä toteuttaa.

<details>
<summary>❌ Ei näin</summary>

```kotlin
interface Worker {
    fun work()
    fun eat()
}

class Robot : Worker {
    override fun work() = println("Työskentelee")
    override fun eat() = throw UnsupportedOperationException("Robotit eivät syö")
}
```

</details>

<details>
<summary>✅ Vaan näin</summary>

```kotlin
interface Workable {
    fun work()
}

interface Eatable {
    fun eat()
}

class Human : Workable, Eatable {
    override fun work() = println("Ihminen työskentelee")
    override fun eat() = println("Ihminen myös syö")
}

class Robot : Workable {
    override fun work() = println("Robotti vain työskentelee")
}
```

</details>

---

### 5. Dependency Inversion Principle

---

_Dependency Inversion Principle_ tarkoittaa riippuvuuksien käänteisyyden periaatetta. Tämän periaatteen mukaan korkean tason moduulien ei tulisi olla riippuvaisia matalan tason moduuleista, vaan molempien tulisi olla riippuvaisia abstraktioista. Periaatteen mukaan tulisi käyttää rajapintoja ja abstrakteja luokkia, jotta korkean tason ohjelmalogiikka ei rikkoudu matalan tason koodin muuttuessa.

**Korkean tason** moduuleilla ja ohjelmalogiikalla tarkoitetaan ohjelmalogiikkaa ja ohjelman ydintoimintoja ("mitä sovellus tekee?"), kuten `service`-luokkia. **Matalan tasolla** tarkoitetaan yksinkertaisia teknisiä toimintoja, kuten tietokantayhteyksiä ja HTTP-pyyntöjä.

<details>
<summary>❌ Ei näin</summary>

```kotlin
class FileUserRepository {
    fun saveUser(username: String) {
        println("Tallennetaan käyttäjä tiedostoon: $username")
    }
}

class UserService {
    private val repository = FileUserRepository()

    fun createUser(username: String) {
        println("Luodaan käyttäjä: $username")
        repository.saveUser(username)
    }
}
```

</details>

<details>
<summary>✅ Vaan näin</summary>

```kotlin
interface UserRepository {
    fun saveUser(username: String)
}

class FileUserRepository : UserRepository {
    override fun saveUser(username: String) {
        println("Tiedostoon tallennettu käyttäjä: $username")
    }
}

class DatabaseUserRepository : UserRepository {
    override fun saveUser(username: String) {
        println("Tietokantaan tallennettu käyttäjä: $username")
    }
}

class UserService(
    private val userRepository: UserRepository
) {
    fun createUser(username: String) {
        println("Luodaan käyttäjä: $username")
        userRepository.saveUser(username)
    }
}
```

```kotlin
fun main() {
    val fileRepo = FileUserRepository()
    val dbRepo = DatabaseUserRepository()

    val serviceWithFile = UserService(fileRepo)
    val serviceWithDb = UserService(dbRepo)

    serviceWithFile.createUser("Anna") // Print: "Tiedostoon tallennettu käyttäjä: Anna"
    serviceWithDb.createUser("Mikko") // Print: "Tietokantaan tallennettu käyttäjä: Mikko"
}
```

</details>

<p align="right">(<a href="#alku">Takaisin alkuun</a>)</p>

## Miten SOLID-periaatteet toteutuvat _Kämppiksessä_?

SOLID-periaatteisiin tutustumisen jälkeen tarkastellaan, millaisia esimerkkejä SOLID-periaatteideiden toteutumisesta tai toteutumattomuudesta voimme löytää _Kämppis_-sovelluksesta ja miten voisimme toteuttaa SOLID-periaatteita paremmin..

Koodiblokeista löytyvät `...` tarkoittavat poistettua osaa koodista, jolla ei ole merkitystä tarkasteltavan tapauksen kannalta. Tällaista tapauksista poistettu koodi voi sisältää esimerkiksi luokkien attribuutteja, metodeja tai riippuvuuksia. Jokaisen periaatteen kohdalla on myös merkitty lähdekoodin sijainti sovelluksen repositoriossa.

<p align="right">(<a href="#alku">Takaisin alkuun</a>)</p>

### Hyviä esimerkkejä SOLID-periaatteista _Kämppiksessä_

Alla olevat koodiblokit sisältävät suoraa lähtekoodia Kämppiksen Git-repositoriosta. Koska nämä koodiblokit edustavat SOLID-periaatteiden mukaisia esimerkkejä, on ne merkitty "✅ Periaatteen mukainen toteutus".

_Kämppis_-sovelluksen lähdekoodista löytyi neljä hyvää esimerkkiä SOLID-periaatteiden toteutumisesta: _[Single Responsibility Principle](#single-responsibility-principle)_, _[Liskov Substitution Principle](#liskov-substitution-principle)_, _[Interface Segregation Principle](#interface-segregation-principle)_ ja _[Dependency Inversion Principle](#dependency-inversion-principle)_. Sovelluksen lähdekoodista ei löytynyt hyvää esimerkkiä _Open-Closed_ -periaatteen toteutumisesta.

#### Single Responsibility Principle

Alla oleva koodiblokki edustaa hyvää esimerkkiä _Single Responsibility_-periaatteen toteutumisesta. `UserController`-luokka on yksi sovelluksemme `controller`-luokista, ja tehtävänsä mukaisesti `UserController` välittää pyyntöjä ja tietoja ohjelmalogiikkaa sisältävälle `service`-luokalle `UserService`. Kontrolleri ei siis itse sisällä ohjelmalogiikkaa, vaan toimii vain välittäjänä.

<details>
<summary>✅ Periaatteen mukainen toteutus</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/UserController.kt`

```kotlin
UserController.kt

@RestController
@RequestMapping("/api/users")
@Validated
class UserController(
    private val userService: UserService
    ) {
    @GetMapping("", "/")
    fun findAll(): ResponseEntity<List<UserDTO>>
            = ResponseEntity.ok(userService.findAll())

    @GetMapping("/{id}")
    fun findUserById(@PathVariable id: Long): ResponseEntity<UserDTO>
            = ResponseEntity.ok(userService.findById(id))

    @GetMapping("/mock")
    fun findAllMock(): ResponseEntity<List<UserDTO>>
            = ResponseEntity.ok(userService.findAllMockUsers())

    @GetMapping("/{id}/copy")
    fun getCopyOfUserData(@PathVariable id: Long): ResponseEntity<UserDataDTO>
            = ResponseEntity.ok(userService.getCopyOfUserData(id))

    @PostMapping
    fun addUser(@Valid @RequestBody request: UserRequest): ResponseEntity<UserDTO>
            = ResponseEntity.status(HttpStatus.CREATED).body(userService.add(request))

    @PutMapping("/{id}")
    fun updateUser(@Valid @RequestBody request: UserRequest, @PathVariable id: Long): ResponseEntity<UserDTO>
            = ResponseEntity.ok(userService.update(request, id))

    @DeleteMapping("/{id}")
    fun deleteById(@PathVariable id: Long): ResponseEntity<Void> {
        userService.delete(id)
        return ResponseEntity(HttpStatus.NO_CONTENT)
    }

    @PutMapping("/{id}/restore")
    fun restoreById(@PathVariable id: Long): ResponseEntity<UserDTO>
        = ResponseEntity.ok(userService.restore(id))

    @GetMapping("/{id}/preferences")
    fun getUserPreferences(@PathVariable id: Long): ResponseEntity<UserPreferenceDTO>
            = ResponseEntity.ok(userService.getPreferences(id))

    @PutMapping("/{id}/preferences")
    fun updateUserPreferences(@Valid @RequestBody request: UserPreferenceRequest, @PathVariable id: Long): ResponseEntity<UserPreferenceDTO>
            = ResponseEntity.ok(userService.updatePreferences(request, id))
}
```

</details>

---

#### Liskov Substitution Principle

_Liskov Substitution_-periaatteen mukaan yläluokan tulee olla korvattavissa aliluokilla ohjelman kohdissa, joissa odotetaan yläluokkaa. _Kämppiksessä_ on yksi ylä-aliluokkarakenne: `Profile`-luokka toimii yläluokkana `UserProfile`- ja `RoomProfile`-aliluokille. _Kämppiksessä_ `Profile`-luokkaa käytetään lähinnä sisältämään `UserProfile`- ja `RoomProfile`-luokkien yhteisiä attribuutteja sekä tarjoamaan `toDTO`-funktio. Aliluokat ylikirjoittavat yläluokan `toDTO`-funktion omilla `toDTO`-funktiollaan käyttämällä `override`-avainsanaa. Lisäksi aliluokilla on omat `ProfileDTO`-rajapintaa implementoivat `UserProfileDTO`- ja `RoomProfileDTO`-luokat. Nimensä mukaisesti `toDTO`-funktio muuttaa kyseisen luokan DTO-luokaksi.

`ProfileService`-luokan `findAll()`-funktio palauttaa `List<ProfileDTO>` eli listan `ProfileDTO`-luokan olioita. Lista voi sisältää `UserProfileDTO`- ja `RoomProfileDTO`-olioita, sillä ne implementoivat `ProfileDTO`-rajapintaa. _Liskov Substitution_-periaate toteutuu, sillä `ProfileDTO`-rajapinta on implementaatioiden ansiosta korvattavissa `UserProfileDTO`- ja `RoomProfileDTO`-olioilla niitä odottavissa kohdissa. `findAll()` myös käsittelee vain `ProfileDTO`-tyyppiä, eli sen ei tarvitse tietää kumpi konkreettinen aliluokka on kyseessä; tämä on tyyppiturvallinen valinta.

<details>
<summary>✅ Periaatteen mukainen toteutus</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/Profile.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/UserProfile.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/RoomProfile.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/ProfileService.kt`

```kotlin
Profile.kt

@Entity
@Table(name = "profiles")
@Inheritance(strategy = InheritanceType.JOINED)
abstract class Profile(
    ...
) {
    ...
    abstract fun toDTO(includeUserSummary: Boolean = false): ProfileDTO // Abstract function to be implemented by subclasses
}

sealed interface ProfileDTO
...
```

```kotlin
UserProfile.kt

@Entity
@Table(name = "user_profiles")
class UserProfile (
    ...
) : Profile() {// Inherits id, bio, photos, and other attributes from Profile
    ...

    override fun toDTO(includeUserSummary: Boolean): UserProfileDTO {
        return UserProfileDTO(
            userId = user.id!!,
            user = if (includeUserSummary) user.toSummaryDTO() else null,
            bio = bio,
            cleanliness = cleanliness,
            pets = pets,
            lifestyle = lifestyle,
            photos = photos.map { it.toProfilePhotoDTO() }
                .toMutableList(),
            id = id
        )
    }

    data class UserProfileDTO(
        ...
    ) : ProfileDTO
    ...
}
```

```kotlin
RoomProfile.kt

@Entity
@Table(name = "room_profiles")
@ValidationService.ValidFurnishedInfo
class RoomProfile(
    ...
): Profile() {
    ...

    override fun toDTO(includeUserSummary: Boolean): RoomProfileDTO {
        return RoomProfileDTO(
            userIds = users.map { it.id!! },
            users = if (includeUserSummary) users.map { it.toSummaryDTO() } else null,
            flat = flat.toDTO(),
            name = name,
            totalRoommates = flat.totalRoommates,
            location = flat.location,
            rent = rent,
            isPrivateRoom = isPrivateRoom,
            furnished = furnished,
            furnishedInfo = furnishedInfo,
            photos = photos.map { it.toProfilePhotoDTO() }
                .toMutableList(),
            bio = bio,
            id = id
        )
    }

    data class RoomProfileDTO(
        ...
    ) : ProfileDTO
    ...
}
```

```kotlin
ProfileService.kt

@Service
class ProfileService(
    private val roomProfileRepository: RoomProfileRepository,
    private val userProfileRepository: UserProfileRepository
) {

    fun findAll(): List<ProfileDTO> {
        val roomProfiles = roomProfileRepository.findAllActive().map { it.toDTO(includeUserSummary = true) }
        val userProfiles = userProfileRepository.findAllActive().map { it.toDTO(includeUserSummary = true) }

        val profiles = roomProfiles + userProfiles

        return profiles
    }
    ...
}
```

</details>

---

#### Interface Segregation Principle

_Interface Segregation_-periaatteen mukaan rajapintaa implementoivien luokkien ei tule joutua toteuttamaan sellaisia metodeja tai tarjoamaan sellasia attribuutteja, joita ne eivät tarvitse. _Kämppiksessä_ tämä toteutuu samoissa rajapinnoissa ja luokissa kuin aiemmassa esimerkissä: `ProfileDTO`, `UserProfileDTO` ja `RoomProfileDTO`. `ProfileDTO`-rajapinta ei pakota sitä implementoivia luokkia `UserProfileDTO` ja `RoomProfileDTO` sisältämään samoja ominaisuuksia, vaan kumpikin DTO-luokka sisältää juuri omiin tarkoituksiinsa tarvitut attribuutit.

<details>
<summary>✅ Periaatteen mukainen toteutus</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/Profile.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/UserProfile.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/RoomProfile.kt`

```kotlin
Profile.kt

sealed interface ProfileDTO
```

```kotlin
UserProfile.kt

data class UserProfileDTO(
    val userId: Long,
    val user: UserSummaryDTO? = null,
    val bio: String,
    val cleanliness: Cleanliness? = null,
    val pets: Pets? = null,
    val lifestyle: MutableSet<Lifestyle>? = mutableSetOf(),
    val photos: MutableList<ProfilePhotoDTO> = mutableListOf(),
    val id: Long? = null
) : ProfileDTO
```

```kotlin
RoomProfile.kt

data class RoomProfileDTO(
    @NotEmpty val userIds : List<Long>,
    val users: List<UserSummaryDTO>? = null,
    val flat: FlatDTO,
    val name: String? = null,
    val totalRoommates: Int,
    val location: City,
    @PositiveOrZero val rent: Int,
    val isPrivateRoom: Boolean,
    val furnished: Boolean,
    val furnishedInfo: String? = null,
    val photos: MutableList<ProfilePhotoDTO>? = mutableListOf(),
    val bio: String,
    val id: Long? = null
) : ProfileDTO
```

</details>

---

#### Dependency Inversion Principle

Alla oleva koodiblokki on hyvä esimerkki _Dependency Inversion_-periaatteen toteutumisesta. Esimerkissä `repository`-rajapinta injektoidaan `service`-luokkaan sen sijaan, että luotaisiin repositoriosta uusi konkreettinen implementaatio (`val feedbackRepository = FeedbackRepository()`). Näin korkean tason moduuli (`FeedbackService`) ei ole suoraan rippuvainen matalan tason moduulista (`FeedbackRepository`) vaan sen injektoidusta abstraktiosta.

_Kämppiksessä_ kaikki `repository`-rajapinnat tarjotaan `service`-luokille injektoimalla `repository`-rajapintojen abstraktiot `service`-luokan käyttöön, eli kaikki nämä noudattavat _Dependency Inversion_-periaatetta.

<details>
<summary>✅ Periaatteen mukainen toteutus</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/FeedbackService.kt`

```kotlin
FeedbackService.kt

@Service
@Validated
class FeedbackService(
    private val repository: FeedbackRepository
) {
    fun findAll(): List<FeedbackDTO> {
        return repository.findAll().map { it.toDTO() }
    }

    fun add(@Valid request: FeedbackDTO): FeedbackDTO {
        val feedback = Feedback(
            feedback = request.feedback
        )

        val addedFeedback = repository.save(feedback)
        return addedFeedback.toDTO()
    }
}
```

</details>

<p align="right">(<a href="#alku">Takaisin alkuun</a>)</p>

### Parannusehdotuksia SOLID-periaatteiden toteutumiseksi _Kämppiksessä_

Alla olevat koodiblokit sisältävät suoraa lähtekoodia _Kämppiksen_ Git-repositoriosta sekä parannusehdotuksia parannellun koodin muodossa. Git-repositoriosta peräisin oleva lähdekoodi on merkitty "❌ Kaipaa parannusta", koska halutaan korostaa sitä, mitä lähdekoodissa voisi parantaa. Vastaavasti parannusehdotus on merkitty "✅ Parannusehdotus".

_Kämppis_-sovelluksen lähdekoodista löytyi kaksi parannusehdotusta SOLID-periaatteiden toteutumiseksi: _[Single Responsibility Principle](#single-responsibility-principle-1)_ ja _[Open-Closed Principle](#open-closed-principle)_. Sovelluksen lähdekoodista ei löytynyt _Liskov Substitution_ -, _Interface Segregation_ - ja _Dependency Inversion_ -periaatteta rikkovia kohtia. Tämä johtunee omien rajapintojen (`interface`) käytön vähyydestä osittain Spring Bootin rajapinta-automaation vuoksi sekä riippuvuusinjektioiden runsaasta käytöstä.

#### Single Responsibility Principle

Alla mainitut tiedostot sisältävät `ProfileController`- ja `ProfileService`-luokat, jotka yhdessä vastaavat käyttäjän tai huoneen profiilin muokkaamisesta. `ProfileController`-luokka sisältää endpointin profiilin muokkaamiseksi. `ProfileController` rikkoo _Single Responsibility_ -periaatetta, sillä se sisältää myös `ProfileService`-luokalle kuuluvaa ohjelmalogiikkaa vaikka kontrollerin tehtävänä on vain välittää pyyntöjä käyttäjältä ja palautuksia käyttäjälle sekä luoda HTTP-vastauksia. Esimerkissä alla on REST API endpoint ja `service`-funktio kutsukoodin luomiseksi.

_Kämppiksessä_ on muitakin `controller`-luokkia, joita vaivaa samanlainen ongelma. Useat kontrollerimme ovat paisuneet sisältämään ohjelmalogiikkaa, jonka kuuluisi olla `service`-luokissa. Alla olevan esimerkin lisäksi esimerkiksi `MessageController.kt` ja `InviteController.kt` ovat tällaisia `controller`-luokkia, joissa _Single Responsibility_-periaate ei toteudu täysin.

<details>
<summary>❌ Kaipaa parannusta</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/ProfileController.kt`

```kotlin
ProfileController.kt

@RestController
@RequestMapping("/api/profiles")
@Validated
class ProfileController(
    ...
) {
    @PutMapping("/{id}")
    fun updateProfile(@Valid @RequestBody profile: ProfileDTO, @PathVariable id: Long): ResponseEntity<ProfileDTO> {
        val userProfile = userProfileRepository.findByIdActive(id)
        val roomProfile = roomProfileRepository.findByIdActive(id)

        when {
            userProfile != null && profile is UserProfileDTO -> {
                val userProfileRequest = UserProfileRequest(
                    userId = profile.userId,
                    bio = profile.bio,
                    cleanliness = profile.cleanliness,
                    lifestyle = profile.lifestyle,
                    pets = profile.pets,
                    photos = profile.photos,
                    id = profile.id
                )
                return ResponseEntity.ok(userProfileService.update(userProfileRequest, id))
            }

            roomProfile != null && profile is RoomProfileDTO -> {
                val roomProfileRequest = RoomProfileRequest(
                    userIds = profile.userIds,
                    flatId = profile.flat.id!!,
                    name = profile.name,
                    rent = profile.rent,
                    isPrivateRoom = profile.isPrivateRoom,
                    furnished = profile.furnished,
                    furnishedInfo = profile.furnishedInfo,
                    bio = profile.bio,
                    id = profile.id
                )
                return ResponseEntity.ok(roomProfileService.update(roomProfileRequest, id))
            }
            else -> return ResponseEntity.notFound().build()
        }
    }
    ...
}
```

</details>

<details>
<summary>✅ Parannusehdotus</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/ProfileController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/ProfileService.kt`

```kotlin
ProfileController.kt

@RestController
@RequestMapping("api/profiles")
@Validated
class ProfileController(
    ...
) {
    @PutMapping("/{id}")
    fun updateProfile(@Valid @RequestBody profile: ProfileDTO, @PathVariable id: Long): ResponseEntity<ProfileDTO> {
        val profileResponse = profileService.updateProfile(profile, id)
        if (profileResponse == null) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.ok(profileResponse)
    }
    ...
}
```

```kotlin
ProfileService.kt

@Service
class ProfileService(
    ...
) {
    ...
    fun updateProfile(profile: ProfileDTO, id: Long): ProfileDTO? {
        val userProfile = userProfileRepository.findByIdActive(id)
        val roomProfile = roomProfileRepository.findByIdActive(id)

        when {
            userProfile != null && profile is UserProfileDTO -> {
                val userProfileRequest = UserProfileRequest(
                    userId = profile.userId,
                    bio = profile.bio,
                    cleanliness = profile.cleanliness,
                    lifestyle = profile.lifestyle,
                    pets = profile.pets,
                    photos = profile.photos,
                    id = profile.id
                )
                return userProfileService.update(userProfileRequest, id)
            }

            roomProfile != null && profile is RoomProfileDTO -> {
                val roomProfileRequest = RoomProfileRequest(
                    userIds = profile.userIds,
                    flatId = profile.flat.id!!,
                    name = profile.name,
                    rent = profile.rent,
                    isPrivateRoom = profile.isPrivateRoom,
                    furnished = profile.furnished,
                    furnishedInfo = profile.furnishedInfo,
                    bio = profile.bio,
                    id = profile.id
                )
                return roomProfileService.update(roomProfileRequest, id)
            }
        }

        return null
    }
    ...
}
```

</details>
<br>

<details>
<summary>Tässä lista kaikista <i>Single Responsibility</i>-periaatteen mukaan refaktoroiduista luokista</summary>

`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/InviteController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/InviteService.kt`

`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/ImageController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/ImageService.kt`

`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/LoginController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/LoginService.kt`

`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/MessageController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/MessageService.kt`

`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/ProfileController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/ProfileService.kt`

`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/SwipeController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/SwipeService.kt`

`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/RoomProfileController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/api/UserProfileController.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/service/QueryService.kt`

</details>

---

#### Open-Closed Principle

Tiedostossa on funktio `getUsersFromProfile`, joka palauttaa `set`in profiiliin lisätyistä käyttäjistä. Funktion nykyisen toteutuksen voidaan katsoa rikkovan _Open-Closed_-periaatetta. Sovellus sisältää kahta eri profiilityyppiä: `UserProfile` ja `RoomProfile`. Mikäli sovellukseen haluttaisiin lisätä jokin kolmas profiilityyppi, esimerkiksi `FlatProfile`, tulisi `getUsersFromProfile`-funktiota myös muokata.

<details>
<summary>❌ Kaipaa parannusta</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/Profile.kt`

```kotlin
Profile.kt

abstract class Profile(
    ...
) {
    ...
}

fun getUsersFromProfile(profile: Profile): Set<User> {
    return when (profile) {
        is UserProfile -> setOf(profile.user) // Single user in UserProfile
        is RoomProfile -> profile.users.toSet() // Multiple users in RoomProfile
        // is FlatProfile -> profile.users.toSet() <-- Open-Closed-periaatteen vastainen muutos
        else -> emptySet()
    }
}
```

</details>

<details>
<summary>✅ Parannusehdotus</summary>

Lähdekoodi:<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/Profile.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/UserProfile.kt`<br>
`kamppis-server/src/main/kotlin/nat20.kamppisserver/domain/RoomProfile.kt`

```kotlin
Profile.kt

abstract class Profile(...) {
    ...
    abstract fun getUsersFromProfile(): Set<User>
}
```

```kotlin
UserProfile.kt

class UserProfile(...) : Profile() {
    ...
    override fun getUsersFromProfile(): Set<User> {
        return setOf(user)
    }
}
```

```kotlin
RoomProfile.kt

class RoomProfile(...) : Profile() {
    ...
    override fun getUsersFromProfile(): Set<User> {
        return users.toSet()
    }
}
```

```kotlin
/* Voidaan myöhemmin lisätä muuttamatta aiempaa koodia

FlatProfile.kt

class FlatProfile(...) : Profile() {
    ...
    override fun getUsersFromProfile(): Set<User> {
        return users.toSet()
    }
}
*/
```

</details>

<p align="right">(<a href="#alku">Takaisin alkuun</a>)</p>

## Lopuksi

_Kämppis_-sovelluksesta löytyi sekä hyviä että parannettavia esimerkkikohtia SOLID-periaatteiden toteutumisesta. Suurimmat muutokset liittyivät _Single Responsibility_-periaatteen toteutumiseen tilanteissa, joissa ohjelmalogiikkaa vuosi `service`-luokista `controller`-luokkiin, vaikka `controller`-luokkien tehtävänä on vain välittää tietoa eikä toteuttaa itse ohjelmalogiikkaa. Lisäksi löydetty _Open-Closed_-periaatteen vastainen kohta koski tilannetta, jossa ohjelmalogiikkaa ei ollut tarkoitettu laajennettavaksi, mutta se ei myöskään ollut kirjoitettu laajentumisen mahdollistavaksi ilman muutoksia muuhun koodiin.

_Kämppis_-sovelluksen back-endin refaktorointi SOLID-periaatteiden mukaiseksi yksinkertaistaa ohjelmalogiikan toimintaa sekä helpottaa koodin ymmärtämistä. Refaktorointi myös mahdollistaa koodin järkevän laajentamisen jatkossa.

<p align="right">(<a href="#alku">Takaisin alkuun</a>)</p>

## Lähteet

Martin, R. 2018. Clean architecture: A Craftsman’s Guide to Software Structure and Design. Pearson Education Inc. https://haaga-helia.finna.fi/Record/nelli21.4920000000457179?sid=5003643650

Millington, S. 26.3.2025. A Solid Guide to SOLID Principles. Baeldung. Luettavissa: https://www.baeldung.com/solid-principles. Luettu: 25.4.2025.

SOLID-koodiesimerkkien pohjat: ChatGPT by OpenAI (luotu 23.4.2025)

<p align="right">(<a href="#alku">Takaisin alkuun</a>)</p>
