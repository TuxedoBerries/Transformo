///--------------------------------------------
/// <summary>
/// Automatic Generated Class - Image
/// Please do not modify
/// Date: 2016-03-12
/// <summary>
///--------------------------------------------
public partial class Image :
	IId,
	ICredits,
	IUrl

{

	/// <summary>
	/// The Id.
	/// </summary>
	private ushort _id;
	/// <summary>
	/// The Credits.
	/// </summary>
	private string _credits;
	/// <summary>
	/// The Url.
	/// </summary>
	private string _url;


	/// <summary>
	/// Initializes a new instance of the <see cref="Image"/> class.
	/// </summary>
	public Image()
	{
		_id = 0;
		_credits = "";
		_url = "";

	}

	public Image(JsonData data)
	{
		_id = data["id"];
		_credits = data["credits"];
		_url = data["url"];

	}

	#region Getters
	/// <summary>
	/// Gets the Id of this Image.
	/// </summary>
	/// <value>The Id.</value>
	public ushort Id {
		get {
			return _id;
		}
	}
	/// <summary>
	/// Gets the Credits of this Image.
	/// </summary>
	/// <value>The Credits.</value>
	public string Credits {
		get {
			return _credits;
		}
	}
	/// <summary>
	/// Gets the Url of this Image.
	/// </summary>
	/// <value>The Url.</value>
	public string Url {
		get {
			return _url;
		}
	}

	#endregion

}
