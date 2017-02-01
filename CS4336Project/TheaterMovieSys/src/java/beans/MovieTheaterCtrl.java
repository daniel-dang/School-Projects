
package beans;
/*
This is the backing bean of the application and this conained most of the attribute that belong
either to the entity or just local attribute that needed for this backing bean.
This backing bean is the middle man between jsf and ejb to process request from jsf and get data
by calling ejb.
*/
import ejb.MovieTheaterEJB;
import entities.Location;
import entities.Movie;
import entities.Showing;
import entities.Showtime;
import entities.Theater;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.EJB;


@Named(value = "movieTheaterCtrl")
@SessionScoped
public class MovieTheaterCtrl implements Serializable {

    @EJB
    private MovieTheaterEJB movieTheaterEjb;
    private Location zipcode;
    private Theater theater;
    private List<Theater> theaterList;
    private Movie movie;
    private List<Movie> movieList;
    private Showtime time;
    private List<Showtime> timeList;
    private List<Showing> showingList;
    private Showing showMovies;
    
    //non entities attributes
    private String input;
    private String theaterName;
    private String movieName;
    private String showtime;
    private List<String> watchTime;
    private String seat;
    
    //card info
    private String cardName;
    private String cardNum;
    private String shortCreditNum;
    private String cardSec;
    private String cardExp;

    public String getShortCreditNum() {
        return shortCreditNum;
    }

    public void setShortCreditNum(String shortCreditNum) {
        this.shortCreditNum = shortCreditNum;
    }

    public List<String> getWatchTime() {
        return watchTime;
    }

    public void setWatchTime(List<String> watchTime) {
        this.watchTime = watchTime;
    }

    public List<Showing> getShowingList() {
        return showingList;
    }

    public void setShowingList(List<Showing> showingList) {
        this.showingList = showingList;
    }

    public Showing getShowMovies() {
        return showMovies;
    }

    public void setShowMovies(Showing showMovies) {
        this.showMovies = showMovies;
    }
    
    public String getTheaterName() {
        return theaterName;
    }

    public void setTheaterName(String theaterName) {
        this.theaterName = theaterName;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }
    
    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public List<Theater> getTheaterList() {
        return theaterList;
    }

    public void setTheaterList(List<Theater> theaterList) {
        this.theaterList = theaterList;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<Showtime> getTimeList() {
        return timeList;
    }

    public void setTimeList(List<Showtime> timeList) {
        this.timeList = timeList;
    }

    public MovieTheaterEJB getMovieTheaterEjb() {
        return movieTheaterEjb;
    }

    public void setMovieTheaterEjb(MovieTheaterEJB movieTheaterEjb) {
        this.movieTheaterEjb = movieTheaterEjb;
    }

    public Location getZipcode() {
        return zipcode;
    }

    public void setZipcode(Location zipcode) {
        this.zipcode = zipcode;
    }

    public Theater getTheater() {
        return theater;
    }

    public void setTheater(Theater theater) {
        this.theater = theater;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Showtime getTime() {
        return time;
    }

    public void setTime(Showtime time) {
        this.time = time;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCardSec() {
        return cardSec;
    }

    public void setCardSec(String cardSec) {
        this.cardSec = cardSec;
    }

    public String getCardExp() {
        return cardExp;
    }

    public void setCardExp(String cardExp) {
        this.cardExp = cardExp;
    }
    
    
    public MovieTheaterCtrl() {}
    
    public String getTheaters(){
        movieTheaterEjb.generateMovie();
        zipcode = movieTheaterEjb.getLocation(input);
        theaterList = movieTheaterEjb.getTheaters(zipcode);
        return "Location.xhtml";
    }
    
    public String getMovies(){
        theater = movieTheaterEjb.getTheater(theaterName);
        showingList = movieTheaterEjb.getTheaterShowing(theater);
        movieList = new ArrayList<>();
        for (int i = 0; i < showingList.size(); i++){
            Movie tempM = movieTheaterEjb.getMovies(showingList.get(i).getMvname().getMvname());
            movieList.add(tempM);
        }
        return "Movies.xhtml";
    }
    
    public String getTimes(){
        movie = movieTheaterEjb.getMovies(movieName);
        timeList = movieTheaterEjb.getShowtimes(movie);
        watchTime = new ArrayList<>();
        for (int i = 0; i < timeList.size(); i++){
            watchTime.add(timeList.get(i).getMvtime());
        }
        return "Time.xhtml";
    }
    
    public String getReceipt(){
        shortCreditNum = cardNum.substring(11, 15);
        return "Receipt.xhtml";
    }
}