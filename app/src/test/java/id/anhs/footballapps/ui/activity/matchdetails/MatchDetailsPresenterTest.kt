package id.anhs.footballapps.ui.activity.matchdetails

import android.content.Context
import id.anhs.footballapps.BasePresenterTest
import id.anhs.footballapps.api.ApiServices
import id.anhs.footballapps.model.Match
import id.anhs.footballapps.model.MatchResponse
import id.anhs.footballapps.model.Team
import id.anhs.footballapps.model.TeamResponse
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.inOrder
import org.mockito.MockitoAnnotations

class MatchDetailsPresenterTest : BasePresenterTest() {

    @Mock
    private lateinit var view: MatchDetailsContract.View

    @Mock
    private lateinit var mContext: Context

    @Mock
    private var theSportDBApiServices = ApiServices.getTheSportDBApiServices()

    private lateinit var presenter: MatchDetailsPresenter

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        presenter = MatchDetailsPresenter(view, mContext, theSportDBApiServices)
    }

    @Test
    fun testLoadMatchDataSuccess() {
        val matches = mutableListOf<Match>().apply {
            add(Match())
        }

        val matchResponse = MatchResponse(matches)
        `when`(theSportDBApiServices.getEventDetails("441613"))
                .thenReturn(Observable.just(matchResponse))

        val inOrder = inOrder(view)
        presenter.loadMatchData("441613")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).setMatchData(matches[0])
    }

    @Test
    fun testLoadMatchDataFailed() {
        val errorMsg = "Load event data failed"
        val error = Throwable(errorMsg)

        `when`(theSportDBApiServices.getEventDetails("441613"))
                .thenReturn(Observable.error(error))

        val inOrder = inOrder(view)
        presenter.loadMatchData("441613")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }

    @Test
    fun testLoadHomeTeamDetailsSuccess() {
        val teams = mutableListOf<Team>().apply {
            add(Team(
                    null,
                    "133604",
                    "Arsenal",
                    "https://www.thesportsdb.com/images/media/team/badge/vrtrtp1448813175.png",
                    "1892",
                    "Emirates Stadium",
                    "Arsenal Football Club is a professional football club based in " +
                            "Holloway, London which currently plays in the Premier League, the highest " +
                            "level of English football. One of the most successful clubs in English " +
                            "football, they have won 13 First Division and Premier League titles and " +
                            "a joint record 11 FA Cups.\r\n\r\nArsenal's success has been particularly " +
                            "consistent: the club has accumulated the second most points in English " +
                            "top-flight football, hold the ongoing record for the longest uninterrupted " +
                            "period in the top flight, and would be placed first in an aggregated " +
                            "league of the entire 20th century. Arsenal is the second side to complete " +
                            "an English top-flight season unbeaten (in the 2003–04 season), " +
                            "playing almost twice as many matches as the previous invincibles " +
                            "Preston North End in the 1888–89 season.\r\n\r\nArsenal was founded " +
                            "in 1886 in Woolwich and in 1893 became the first club from the south " +
                            "of England to join the Football League. In 1913, they moved north across " +
                            "the city to Arsenal Stadium in Highbury. In the 1930s, they won five " +
                            "League Championship titles and two FA Cups. After a lean period in the" +
                            " post-war years they won the League and FA Cup Double, in the 1970–71 season, " +
                            "and in the 1990s and first decade of the 21st century, won two more Doubles " +
                            "and reached the 2006 UEFA Champions League Final. Since neighbouring " +
                            "Tottenham Hotspur, the two clubs have had a fierce rivalry, the North " +
                            "London derby.\r\n\r\nArsenal have one of the highest incomes and largest " +
                            "fanbases in the world. The club was named the fifth most valuable association " +
                            "football club in the world, valued at £1.3 billion in 2014."
            ))
        }

        val teamResponse = TeamResponse(teams)
        `when`(theSportDBApiServices.getTeam("133604"))
                .thenReturn(Observable.just(teamResponse))

        val inOrder = inOrder(view)
        presenter.loadHomeTeamDetails("133604")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).setHomeTeamBadge(teams[0].teamBadge ?: "")
    }

    @Test
    fun testLoadHomeTeamDetailsFailed() {
        val errorMsg = "Load team data failed"
        val error = Throwable(errorMsg)

        `when`(theSportDBApiServices.getTeam("133604"))
                .thenReturn(Observable.error(error))

        val inOrder = inOrder(view)
        presenter.loadHomeTeamDetails("133604")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }

    @Test
    fun testLoadAwayTeamDetailsSuccess() {
        val teams = mutableListOf<Team>().apply {
            add(Team(
                    null,
                    "134301",
                    "Bournemouth",
                    "https://www.thesportsdb.com/images/media/team/badge/y08nak1534071116.png",
                    "1890",
                    "Dean Court",
                    "A.F.C. Bournemouth is a football club playing in the Championship, " +
                            "the second tier in the English football league system. The club plays at " +
                            "Dean Court in Kings Park, Boscombe, Bournemouth, Dorset and have been in " +
                            "existence since 1899.\r\n\r\nNicknamed The Cherries, the team traditionally" +
                            " played in red shirts with white sleeves until 1971, when the strip was changed " +
                            "to red and black stripes, similar to that of A.C. Milan. A predominantly " +
                            "red shirt was chosen for the 2004–05 and 2005–06 seasons before announcing " +
                            "a return to the stripes for the 2006–07 season due to fan demand.\r\n\r\nAfter " +
                            "narrowly avoiding relegation from the Football League in the 2008–09 season, " +
                            "Bournemouth were promoted to League One at the end of the 2009–10. After making " +
                            "the League One play-off semi-finals in 2010–11 and achieving a mid-table finish " +
                            "in 2011–12, Bournemouth won promotion to the Championship at the end of the 2012–13 " +
                            "season, putting them in the second tier of the league for only the second time in " +
                            "their history.\r\n\r\nAlthough the exact date of the club's foundation is not known, " +
                            "there is proof that it was formed in the autumn of 1899 out of the remains of the older " +
                            "Boscombe St. John's Lads’ Institute F.C. The club was originally known as Boscombe F.C.. " +
                            "The first President was Mr. J.C. Nutt.\r\n\r\nIn their first season 1889–90 Boscombe F.C. " +
                            "competed in the Bournemouth and District Junior League. They also played in the Hants Junior Cup. " +
                            "During the first two seasons they played on a football pitch in Castlemain Avenue, Pokesdown. " +
                            "From their third season the team played on a pitch in King's Park. In the season of 1905–06 " +
                            "Boscombe F.C. graduated to senior amateur football.\r\n\r\nIn 1910 the club was granted a " +
                            "long lease upon some wasteland next to Kings Park, as the clubs football ground, by their " +
                            "president Mr. J.E. Cooper-Dean. With their own ground, named Dean Court after the benefactor, " +
                            "the club continued to thrive and dominated the local football scene. Also in 1910 the club " +
                            "signed their first professional football player B. Penton.\r\n\r\nAround about this time the " +
                            "club obtained their nickname 'The Cherries'. Foremost there are two tales on how the club " +
                            "gained this pet name. First, because of the cherry-red striped shirts that the team played in " +
                            "and, perhaps more plausible, because Dean Court was built adjacent to the Cooper-Dean estate," +
                            " which encompassed numerous cherry orchards.\r\n\r\nFor the first time during the season of " +
                            "1913–14 the club competed in the F.A. Cup. The clubs progress was halted in 1914 with the " +
                            "outbreak of the war and Boscombe F.C. returned to the Hampshire league.\r\n\r\nIn 1920 the " +
                            "Third Division was formed and Boscombe were promoted to the Southern League, with moderate success."
            ))
        }

        val teamResponse = TeamResponse(teams)
        `when`(theSportDBApiServices.getTeam("134301"))
                .thenReturn(Observable.just(teamResponse))

        val inOrder = inOrder(view)
        presenter.loadAwayTeamDetails("134301")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).setAwayTeamBadge(teams[0].teamBadge ?: "")
    }

    @Test
    fun testLoadAwayTeamDetailsFailed() {
        val errorMsg = "Load team data failed"
        val error = Throwable(errorMsg)

        `when`(theSportDBApiServices.getTeam("134301"))
                .thenReturn(Observable.error(error))

        val inOrder = inOrder(view)
        presenter.loadAwayTeamDetails("134301")
        inOrder.verify(view).loadingData(true)
        inOrder.verify(view).loadingData(false)
        inOrder.verify(view).showToastMsg(ArgumentMatchers.anyString())
    }
}