package com.nilevia.dogbro.features.learn.domain.mapper

import com.nilevia.dogbro.data.local.entities.BreedEntity
import com.nilevia.dogbro.features.learn.domain.models.Breed
import org.junit.Test

class BreedMapperTest {
    @Test
    fun `mapToUi maps list correctly`() {
        val entities = listOf(BreedEntity("a", "b"))
        val results = entities.mapToUi()
        assert(results.size == 1)
        assert(results[0].main == "a")
        assert(results[0].sub == "b")
    }

    @Test
    fun `mapToUi handles null list`() {
        val results = (null as List<BreedEntity>?).mapToUi()
        assert(results.isEmpty())
    }

    @Test
    fun `mapToUi handles empty list`() {
        val results = emptyList<BreedEntity>().mapToUi()
        assert(results.isEmpty())
    }

    @Test
    fun `getTitle formats correctly`() {
        val breed = Breed("labrador", "retriever")
        val title = breed.getTitle()
        assert(title == "Labrador - Retriever")
    }

    @Test
    fun `getTitle formats main only`() {
        val breed = Breed("beagle", null)
        val title = breed.getTitle()
        assert(title == "Beagle")
    }
} 