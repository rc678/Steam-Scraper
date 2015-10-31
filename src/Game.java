/**
 * class contains information on games found in steam store
 */
public class Game
{
    String _appID;
    String _name;
    String _date;
    String _discount;
    String _originalPrice;
    String _salePrice;
    String _photoUrl;
    String _gameUrl;
    String _rating;
    String _ratingDescription;

    /**
     *
     * @param appID
     * @param gameName
     * @param date
     * @param discount
     * @param originalPrice
     * @param salePrice
     * @param photoUrl
     */
    public Game(String appID, String gameName, String date, String discount,
                String originalPrice, String salePrice, String photoUrl)
    {
        this._appID = appID;
        this._name = gameName;
        this._date = date;
        this._discount = discount;
        this._originalPrice = originalPrice;
        this._salePrice = salePrice;
        this._photoUrl = photoUrl;
    }

    public Game()
    {

    }

    public String getAppID() {
        return _appID;
    }

    public String getDate() {
        return _date;
    }

    public void setDate(String date) {
        this._date = date;
    }

    public void setAppID(String appID) {
        this._appID = appID;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        this._name = name;
    }
    public String getDiscount() {
        return _discount;
    }

    public void setDiscount(String discount) {
        this._discount = discount;
    }

    public String getOriginalPrice() {
        return _rating;
    }

    public void setOriginalPrice(String originalPrice) {
        this._originalPrice = originalPrice;
    }

    public String getSalePrice() {
        return _salePrice;
    }

    public void setSalePrice(String price) {
        this._salePrice = price;
    }

    public String getPhotoUrl() {
        return _photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this._photoUrl = photoUrl;
    }

    public String getGameUrl() {
        return _gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this._gameUrl = gameUrl;
    }

    public String getRating() {
        return _rating;
    }

    public void setRating(String rating) {
        this._rating = rating;
    }

    public String getRatingDescription() {
        return _ratingDescription;
    }

    public void setRatingDescription(String ratingDescription) {
        this._ratingDescription = ratingDescription;
    }
}
