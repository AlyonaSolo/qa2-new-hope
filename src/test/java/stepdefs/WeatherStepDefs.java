package stepdefs;

import Requesters.WeatherRequester;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Weather;
import model.WeatherResponse;
import org.junit.jupiter.api.Assertions;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WeatherStepDefs {
    private long cityId;
    private WeatherResponse response;

    @Given("city ID: {long}")
    public void set_city_id(long cityId) {
        this.cityId = cityId;
    }

    @When("we are requesting weather data")
    public void request_weather() throws JsonProcessingException {
        WeatherRequester requester = new WeatherRequester();
        response = requester.getWeatherData(cityId);
    }

    @Then("cordinates are:")
    public void check_coords(Map<String, Double> coords) {
        Assertions.assertEquals(coords.get("lon"), response.getCoord().getLon(), "Incorrect Coord lon");
        Assertions.assertEquals(coords.get("lat"), response.getCoord().getLat(), "Incorrect Coord lat");

    }

    @Then("weathers are:")
    public void check_weathers(DataTable dataTable) {
        List<Map<String, String>> weathers = dataTable.asMaps();

        Assertions.assertEquals(weathers.size(), response.getWeathers().size(), "Wrong size");

        for (int i = 0; i < weathers.size(); i++) {
            Map<String, String> expectedWeather = weathers.get(i);
            Weather actualWeather = response.getWeathers().get(i);

            Assertions.assertEquals(Long.parseLong(expectedWeather.get("id")), actualWeather.getId(), "Wrong Id");
            Assertions.assertEquals(expectedWeather.get("main"), actualWeather.getMain(), "Wrong Main");
            Assertions.assertEquals(expectedWeather.get("description"), actualWeather.getDescription(), "Wrong Description");
            Assertions.assertEquals(expectedWeather.get("icon"), actualWeather.getIcon(), "Wrong icon");
        }
    }

    @Then("base is {string}")
    public void check_base(String base) {
        Assertions.assertEquals(base, response.getBase(), "Wrong base");
    }

    @Then("main are:")
    public void check_main(Map<String, Double> main) {
        Assertions.assertEquals(main.get("temp"), response.getMain().getTemp(), "Wrong Temp");
        Assertions.assertEquals(main.get("pressure"), response.getMain().getPressure(), "Wrong Pressure");
        Assertions.assertEquals(main.get("humidity"), response.getMain().getHumidity(), "Wrong Humidity");
        Assertions.assertEquals(main.get("temp_min"), response.getMain().getTemp_min(), "Wrong Temp_min");
        Assertions.assertEquals(main.get("temp_max"), response.getMain().getTemp_max(), "Wrong Temp_max");

    }

    @Then("visibility is {int}")
    public void check_visibility(int visibility) {
        Assertions.assertEquals(visibility, response.getVisibility(), "Wrong Visibility");
    }

    @Then("wind are:")
    public void check_wind(Map<String, Double> wind) {
        Assertions.assertEquals(wind.get("speed"), response.getWind().getSpeed(), "Wrong Speed");
        Assertions.assertEquals(wind.get("deg"), response.getWind().getDeg(), "Wrong Deg");

    }

    @Then("clouds are:")
    public void check_clouds(Map<String, Integer> clouds) {
        Assertions.assertEquals(clouds.get("all"), response.getClouds().getAll(), "Wrong Clouds");
    }

    @Then("dt is {int}")
    public void check_dt(int dt) {
        Assertions.assertEquals(dt, response.getDt(), "Wrong Dt");
    }

    @Then("sys is:")
    public void check_sys(Map<String, String> params) {

        Assertions.assertEquals(Integer.parseInt(params.get("type")), response.getSys().getType(), "Wrong Type");
        Assertions.assertEquals(Long.parseLong(params.get("id")), response.getSys().getId(), "Wrong Id");
        Assertions.assertEquals(Double.parseDouble(params.get("message")), response.getSys().getMessage(), "Wrong Message");
        Assertions.assertEquals(params.get("country"), response.getSys().getCountry(), "Wrong Country");
        Assertions.assertEquals(Long.parseLong(params.get("sunrise")), response.getSys().getSunrise(), "Wrong Sunrise");
        Assertions.assertEquals(Long.parseLong(params.get("sunset")), response.getSys().getSunset(), "Wrong Sunset");

        LocalDate date = Instant.ofEpochSecond(response.getSys().getSunrise()).atZone(ZoneId.systemDefault()).toLocalDate();
        System.out.println(date);


        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(response.getSys().getSunrise()), ZoneId.systemDefault());
        System.out.println(dateTime);

        System.out.println(LocalDateTime.now().plusDays(6).getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.GERMAN));
    }


    @Then("id is {int}")
    public void check_id(int id) {
        Assertions.assertEquals(id, response.getId(), "Wrong Id");
    }

    @Then("name is {string}")
    public void name_is(String name) {
        Assertions.assertEquals(name, response.getName(), "Wrong Name");
    }

    @Then("cod is {int}")
    public void check_cod(int cod) {
        Assertions.assertEquals(cod, response.getCod(), "Wrong Cod");
    }

}
