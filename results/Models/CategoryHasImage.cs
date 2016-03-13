///--------------------------------------------
/// <summary>
/// Automatic Generated Class - Category Has Image
/// Please do not modify
/// Date: 2016-03-12
/// <summary>
///--------------------------------------------
public partial class CategoryHasImage :
	ICategoryid,
	IImageid

{

	/// <summary>
	/// The Categoryid.
	/// </summary>
	private ushort _categoryId;
	/// <summary>
	/// The Imageid.
	/// </summary>
	private ushort _imageId;


	/// <summary>
	/// Initializes a new instance of the <see cref="CategoryHasImage"/> class.
	/// </summary>
	public CategoryHasImage()
	{
		_categoryId = 0;
		_imageId = 0;

	}

	public CategoryHasImage(JsonData data)
	{
		_categoryId = data["category_id"];
		_imageId = data["image_id"];

	}

	#region Getters
	/// <summary>
	/// Gets the Categoryid of this Category Has Image.
	/// </summary>
	/// <value>The Categoryid.</value>
	public ushort Categoryid {
		get {
			return _categoryId;
		}
	}
	/// <summary>
	/// Gets the Imageid of this Category Has Image.
	/// </summary>
	/// <value>The Imageid.</value>
	public ushort Imageid {
		get {
			return _imageId;
		}
	}

	#endregion

}
