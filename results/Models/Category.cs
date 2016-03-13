///--------------------------------------------
/// <summary>
/// Automatic Generated Class - Category
/// Please do not modify
/// Date: 2016-03-12
/// <summary>
///--------------------------------------------
public partial class Category :
	IId,
	IName

{

	/// <summary>
	/// The Id.
	/// </summary>
	private ushort _id;
	/// <summary>
	/// The Name.
	/// </summary>
	private string _name;


	/// <summary>
	/// Initializes a new instance of the <see cref="Category"/> class.
	/// </summary>
	public Category()
	{
		_id = 0;
		_name = "";

	}

	public Category(JsonData data)
	{
		_id = data["id"];
		_name = data["name"];

	}

	#region Getters
	/// <summary>
	/// Gets the Id of this Category.
	/// </summary>
	/// <value>The Id.</value>
	public ushort Id {
		get {
			return _id;
		}
	}
	/// <summary>
	/// Gets the Name of this Category.
	/// </summary>
	/// <value>The Name.</value>
	public string Name {
		get {
			return _name;
		}
	}

	#endregion

}
