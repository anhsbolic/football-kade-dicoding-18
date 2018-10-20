package id.anhs.footballapps

import id.anhs.footballapps.utils.RxSchedulersRule
import org.junit.ClassRule

open class BasePresenterTest {

    companion object {
        @ClassRule
        @JvmField
        val schedulers = RxSchedulersRule()
    }
}