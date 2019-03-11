package com.mighty16.testapp

import com.mighty16.testapp.domain.FeedEntity
import org.junit.Assert
import org.junit.Test

class DomainModelsTest {

    @Test
    fun feedEntity() {
        val feedEntity1 = FeedEntity(listOf(), listOf(), 0)
        val feedEntity2 = FeedEntity(listOf(), listOf(), 1)
        val feedEntity3 = FeedEntity(listOf(), listOf(), 0)

        Assert.assertNotEquals("FeedEntity objects must be different", feedEntity1, feedEntity2)
        Assert.assertEquals("FeedEntity objects must be equal", feedEntity1, feedEntity3)
        Assert.assertNotEquals("FeedEntity objects must be different from String", feedEntity1, "Test")

        Assert.assertEquals("FeedEntity hashcode must be equal", feedEntity1.hashCode(), feedEntity3.hashCode())
        Assert.assertNotEquals("FeedEntity hashcode must be different", feedEntity1.hashCode(), feedEntity2.hashCode())

        Assert.assertEquals("FeedEntity toString must be equal", feedEntity1.toString(), feedEntity3.toString())
        Assert.assertNotEquals("FeedEntity toString must be different", feedEntity1.toString(), feedEntity2.toString())
    }

    @Test
    fun photoEntity() {
        val photo1 = TestRepository.createTestPhoto(1)
        val photo2 = TestRepository.createTestPhoto(2)
        val photo3 = TestRepository.createTestPhoto(1)

        Assert.assertNotEquals("PhotoEntity objects must be different", photo1, photo2)
        Assert.assertEquals("PhotoEntity objects must be equal", photo1, photo3)

        Assert.assertNotEquals("PhotoEntity objects must be different from String", photo1, "Test")

        Assert.assertEquals("PhotoEntity hashcode must be equal", photo1.hashCode(), photo3.hashCode())
        Assert.assertNotEquals("PhotoEntity hashcode must be different", photo1.hashCode(), photo2.hashCode())
        Assert.assertEquals("PhotoEntity toString must be equal", photo1.toString(), photo3.toString())
        Assert.assertNotEquals("PhotoEntity toString must be different", photo1.toString(), photo2.toString())
    }

    @Test
    fun postEntity(){
        val post1 = TestRepository.createTestPost(1,"title","text")
        val post2 = TestRepository.createTestPost(2,"title 2","text 2")
        val post3 = TestRepository.createTestPost(1,"title","text")

        Assert.assertNotEquals("PostEntity objects must be different", post1, post2)
        Assert.assertEquals("PostEntity objects must be equal", post1, post3)
        Assert.assertNotEquals("PostEntity objects must be different from String", post1, "Test")

        Assert.assertEquals("PostEntity hashcode must be equal", post1.hashCode(), post3.hashCode())
        Assert.assertNotEquals("PostEntity hashcode must be different", post1.hashCode(), post2.hashCode())
        Assert.assertEquals("PostEntity toString must be equal", post1.toString(), post3.toString())
        Assert.assertNotEquals("PostEntity toString must be different", post1.toString(), post2.toString())
    }

}