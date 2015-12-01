/**
 *
 */
public class TopSeller
{

    private String _appid;
    private String _gameName;
    private String _popularityRank;

    public TopSeller(String appid, String gameName, String popularityRank)
    {
        this._appid = appid;
        this._gameName = gameName;
        this._popularityRank = popularityRank;
    }

    public TopSeller()
    {

    }

    public String getAppid()
    {
        return this._appid;
    }

    public void setAppid(String appid)
    {
        this._appid = appid;
    }

    public String getGameName(String gameName)
    {
        return this._gameName = gameName;
    }

    public void setGameName(String gameName)
    {
        this._gameName = gameName;
    }

    public String getPopularityRank()
    {
        return this._popularityRank;
    }

    public void setPopularityRank(String rank)
    {
        this._popularityRank = rank;
    }




}
