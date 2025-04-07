package nat20.kamppisserver.configuration.mockdataconfigs

import nat20.kamppisserver.domain.ProfilePhoto
import nat20.kamppisserver.repository.ProfilePhotoRepository
import nat20.kamppisserver.repository.ProfileRepository

class MockProfilePhotosConfig {

    /**
     * Database initializer for adding mock profile photo data into the database
     */
    fun insertMockProfilePhotosToDatabase(
        profileRepository: ProfileRepository,
        profilePhotoRepository: ProfilePhotoRepository
    ) {
        // These are profile photos for user profiles
        val userProfilePhotos = mutableListOf(
            ProfilePhoto(
                profile = profileRepository.findById(1L).get(),
                url = "https://cdn.stocksnap.io/img-thumbs/960w/woman-portrait_CLTJPNEBUL.jpg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(1L).get(),
                url = "https://images.pexels.com/photos/7508810/pexels-photo-7508810.jpeg",
                isProfilePhoto = false,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(1L).get(),
                url = "https://images.pexels.com/photos/7994283/pexels-photo-7994283.jpeg",
                isProfilePhoto = false,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(2L).get(),
                url = "https://freerangestock.com/sample/169954/young-man-in-contemplative-urban-scene.jpg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(2L).get(),
                url = "https://images.pexels.com/photos/7252531/pexels-photo-7252531.jpeg",
                isProfilePhoto = false,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(3L).get(),
                url = "https://cdn.stocksnap.io/img-thumbs/960w/business-man_IVZBYWKEFM.jpg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(3L).get(),
                url = "https://images.pexels.com/photos/1472856/pexels-photo-1472856.jpeg",
                isProfilePhoto = false,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(4L).get(),
                url = "https://upload.wikimedia.org/wikipedia/commons/2/2d/African-Woman-Business-Woman-Young-Woman-Black-Woman-3439224.jpg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(4L).get(),
                url = "https://images.pexels.com/photos/6550399/pexels-photo-6550399.jpeg",
                isProfilePhoto = false,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(5L).get(),
                url = "https://live.staticflickr.com/2727/4523649809_f893abca83_b.jpg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(5L).get(),
                url = "https://images.pexels.com/photos/7849189/pexels-photo-7849189.jpeg",
                isProfilePhoto = false,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(6L).get(),
                url = "https://images.pexels.com/photos/6274712/pexels-photo-6274712.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(7L).get(),
                url = "https://images.pexels.com/photos/415829/pexels-photo-415829.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(8L).get(),
                url = "https://images.pexels.com/photos/6274712/pexels-photo-6274712.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(9L).get(),
                url = "https://images.pexels.com/photos/7275385/pexels-photo-7275385.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(10L).get(),
                url = "https://images.pexels.com/photos/4307869/pexels-photo-4307869.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(11L).get(),
                url = "https://images.pexels.com/photos/5393594/pexels-photo-5393594.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(12L).get(),
                url = "https://images.pexels.com/photos/5384445/pexels-photo-5384445.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(13L).get(),
                url = "https://images.pexels.com/photos/6976943/pexels-photo-6976943.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(14L).get(),
                url = "https://images.pexels.com/photos/5490276/pexels-photo-5490276.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(15L).get(),
                url = "https://images.pexels.com/photos/8420889/pexels-photo-8420889.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(16L).get(),
                url = "https://images.pexels.com/photos/8090137/pexels-photo-8090137.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(17L).get(),
                url = "https://images.pexels.com/photos/3796217/pexels-photo-3796217.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(18L).get(),
                url = "https://images.pexels.com/photos/5876695/pexels-photo-5876695.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(19L).get(),
                url = "https://images.pexels.com/photos/7745573/pexels-photo-7745573.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(20L).get(),
                url = "https://images.pexels.com/photos/6000065/pexels-photo-6000065.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(21L).get(),
                url = "https://images.pexels.com/photos/3586091/pexels-photo-3586091.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(22L).get(),
                url = "https://images.pexels.com/photos/7116213/pexels-photo-7116213.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(23L).get(),
                url = "https://images.pexels.com/photos/6608313/pexels-photo-6608313.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(24L).get(),
                url = "https://images.pexels.com/photos/2589653/pexels-photo-2589653.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(25L).get(),
                url = "https://images.pexels.com/photos/774909/pexels-photo-774909.jpeg",
                isProfilePhoto = true,
            ),
            ProfilePhoto(
                profile = profileRepository.findById(26L).get(),
                url = "https://images.pexels.com/photos/1040881/pexels-photo-1040881.jpeg",
                isProfilePhoto = true,
            ),
        )

        // These are profile photos for room profiles
        val roomProfilePhotos = mutableListOf(
            ProfilePhoto(
                profile = profileRepository.findById(79L).get(),
                url = "https://images.pexels.com/photos/2416932/pexels-photo-2416932.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(79L).get(),
                url = "https://images.pexels.com/photos/2416931/pexels-photo-2416931.jpeg",
                isProfilePhoto = false
            ),
            ProfilePhoto(
                profile = profileRepository.findById(79L).get(),
                url = "https://images.pexels.com/photos/2416933/pexels-photo-2416933.jpeg",
                isProfilePhoto = false
            ),
            ProfilePhoto(
                profile = profileRepository.findById(80L).get(),
                url = "https://images.pexels.com/photos/1743229/pexels-photo-1743229.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(80L).get(),
                url = "https://images.pexels.com/photos/1743231/pexels-photo-1743231.jpeg",
                isProfilePhoto = false
            ),
            ProfilePhoto(
                profile = profileRepository.findById(81L).get(),
                url = "https://images.pexels.com/photos/3935340/pexels-photo-3935340.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(82L).get(),
                url = "https://images.pexels.com/photos/1666389/pexels-photo-1666389.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(82L).get(),
                url = "https://images.pexels.com/photos/1666388/pexels-photo-1666388.jpeg",
                isProfilePhoto = false
            ),
            ProfilePhoto(
                profile = profileRepository.findById(83L).get(),
                url = "https://images.pexels.com/photos/817781/pexels-photo-817781.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(83L).get(),
                url = "https://images.pexels.com/photos/817781/pexels-photo-817781.jpeg",
                isProfilePhoto = false
            ),
            ProfilePhoto(
                profile = profileRepository.findById(84L).get(),
                url = "https://images.pexels.com/photos/2631746/pexels-photo-2631746.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(85L).get(),
                url = "https://images.pexels.com/photos/7031616/pexels-photo-7031616.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(86L).get(),
                url = "https://images.pexels.com/photos/237371/pexels-photo-237371.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(87L).get(),
                url = "https://images.pexels.com/photos/271649/pexels-photo-271649.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(88L).get(),
                url = "https://images.pexels.com/photos/271624/pexels-photo-271624.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(89L).get(),
                url = "https://images.pexels.com/photos/31495986/pexels-photo-31495986/free-photo-of-serene-minimalist-interior-with-soft-light.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(90L).get(),
                url = "https://images.pexels.com/photos/210604/pexels-photo-210604.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(91L).get(),
                url = "https://images.pexels.com/photos/1879061/pexels-photo-1879061.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(92L).get(),
                url = "https://images.pexels.com/photos/8146335/pexels-photo-8146335.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(93L).get(),
                url = "https://images.pexels.com/photos/2082087/pexels-photo-2082087.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(94L).get(),
                url = "https://images.pexels.com/photos/11784665/pexels-photo-11784665.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(95L).get(),
                url = "https://images.pexels.com/photos/1743231/pexels-photo-1743231.jpeg",
                isProfilePhoto = true
            ),
            ProfilePhoto(
                profile = profileRepository.findById(96L).get(),
                url = "https://images.pexels.com/photos/4553183/pexels-photo-4553183.jpeg",
                isProfilePhoto = true
            )
        )

        // Save photos to database
        profilePhotoRepository.saveAll(userProfilePhotos + roomProfilePhotos)
    }
}