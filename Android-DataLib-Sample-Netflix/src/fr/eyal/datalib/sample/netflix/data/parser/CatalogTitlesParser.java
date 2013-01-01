package fr.eyal.datalib.sample.netflix.data.parser;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import fr.eyal.lib.data.model.ResponseBusinessObject;
import fr.eyal.lib.data.parser.GenericHandler;
import fr.eyal.datalib.sample.netflix.data.model.catalogtitles.*;


public class CatalogTitlesParser extends DefaultHandler implements GenericHandler {

    private static final String TAG = CatalogTitlesParser.class.getSimpleName();

    public static final int UNKNOWN = -1;

	//CatalogTitles
	private static final int CATALOG_TITLES = 0;
	private static final int CATALOG_TITLES_NUMBER_OF_RESULTS = 1;
	private static final int CATALOG_TITLES_START_INDEX = 2;
	private static final int CATALOG_TITLES_RESULTS_PER_PAGE = 3;
	private static final int CATALOG_TITLES_CATALOG_TITLE = 4;
	private static final int CATALOG_TITLES_CATALOG_TITLE_ID = 5;
	private static final int CATALOG_TITLES_CATALOG_TITLE_TITLE = 6;
	private static final int CATALOG_TITLES_CATALOG_TITLE_BOX_ART = 7;
	private static final int CATALOG_TITLES_CATALOG_TITLE_RELEASE_YEAR = 8;
	private static final int CATALOG_TITLES_CATALOG_TITLE_CATEGORY = 9;
	private static final int CATALOG_TITLES_CATALOG_TITLE_LINK = 10;
	private static final int CATALOG_TITLES_CATALOG_TITLE_RUNTIME = 11;
	private static final int CATALOG_TITLES_CATALOG_TITLE_AVERAGE_RATING = 12;
    
	private int mState = UNKNOWN;

    private final StringBuilder mBuilder = new StringBuilder();
	private CatalogTitles catalogTitles;
	private CatalogTitle catalogTitle;
	private Category category;
	private Link link;

    public CatalogTitlesParser() {
	catalogTitles = new CatalogTitles();
	catalogTitles.catalogTitle = new ArrayList<CatalogTitle>();
	}

    @Override
    public ResponseBusinessObject getParsedData() {
        return catalogTitles;
    }

    @Override
    public void startElement(final String uri, final String localName, final String qName, final Attributes attributes) throws SAXException {

        mBuilder.setLength(0);

        switch (mState) {

			case UNKNOWN:
			    if (qName.equals("catalog_titles")) {
			        mState = CATALOG_TITLES;
			        catalogTitles = new CatalogTitles();
			        catalogTitles.catalogTitle = new ArrayList<CatalogTitle>();
					
			    }
			    break;
			
			case CATALOG_TITLES:
			    if (qName.equals("number_of_results")) {
					mState = CATALOG_TITLES_NUMBER_OF_RESULTS;
					
			    }
				else if (qName.equals("start_index")) {
					mState = CATALOG_TITLES_START_INDEX;
					
			    }
				else if (qName.equals("results_per_page")) {
					mState = CATALOG_TITLES_RESULTS_PER_PAGE;
					
			    }
				else if (qName.equals("catalog_title")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
			        catalogTitle = new CatalogTitle();
					
			    }
			    break;
			
			
			
			
			case CATALOG_TITLES_CATALOG_TITLE:
			
			    if (qName.equals("id")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_ID;
					
			    }
				else if (qName.equals("title")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_TITLE;
					catalogTitle.attrTitleShort = attributes.getValue("short");
					catalogTitle.attrTitleRegular = attributes.getValue("regular");
			    }
				else if (qName.equals("box_art")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_BOX_ART;
					catalogTitle.attrBox_artSmall = attributes.getValue("small");
					catalogTitle.attrBox_artMedium = attributes.getValue("medium");
					catalogTitle.attrBox_artLarge = attributes.getValue("large");
			    }
				else if (qName.equals("release_year")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_RELEASE_YEAR;
					
			    }
				else if (qName.equals("category")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE_CATEGORY;
			        category = new Category();
					category.attrLabel = attributes.getValue("label");
					category.attrTerm = attributes.getValue("term");
			    }
				else if (qName.equals("link")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE_LINK;
			        link = new Link();
					link.attrHref = attributes.getValue("href");
					link.attrRel = attributes.getValue("rel");
					link.attrTitle = attributes.getValue("title");
			    }
				else if (qName.equals("runtime")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_RUNTIME;
					
			    }
				else if (qName.equals("average_rating")) {
					mState = CATALOG_TITLES_CATALOG_TITLE_AVERAGE_RATING;
					
			    }
			    break;

            default:
                // do nothing
                break;
        }
    }

    @Override
    public void characters(final char[] ch, final int start, final int length) throws SAXException {
        mBuilder.append(ch, start, length);
    }

    @Override
    public void endElement(final String uri, final String localName, final String qName) throws SAXException {

        switch (mState) {
			
			case CATALOG_TITLES:
			    if (qName.equals("catalog_titles")) {
			        mState = UNKNOWN;
			    }
			    break;
			
			case CATALOG_TITLES_NUMBER_OF_RESULTS:
			    if (qName.equals("number_of_results")) {
			        mState = UNKNOWN;
					catalogTitles.number_of_results = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_START_INDEX:
			    if (qName.equals("start_index")) {
			        mState = UNKNOWN;
					catalogTitles.start_index = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_RESULTS_PER_PAGE:
			    if (qName.equals("results_per_page")) {
			        mState = UNKNOWN;
					catalogTitles.results_per_page = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE:
			    if (qName.equals("catalog_title")) {
			        mState = CATALOG_TITLES;
					catalogTitles.catalogTitle.add(catalogTitle);
			    }
			    break;
				
			
			case CATALOG_TITLES_CATALOG_TITLE_ID:
			    if (qName.equals("id")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.id = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_TITLE:
			    if (qName.equals("title")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.title = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_BOX_ART:
			    if (qName.equals("box_art")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.box_art = mBuilder.toString();
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_RELEASE_YEAR:
			    if (qName.equals("release_year")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.release_year = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_RUNTIME:
			    if (qName.equals("runtime")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.runtime = Integer.parseInt(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_AVERAGE_RATING:
			    if (qName.equals("average_rating")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.average_rating = Float.parseFloat(mBuilder.toString());
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_CATEGORY:
			    if (qName.equals("category")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.category.add(category);
			    }
			    break;
			case CATALOG_TITLES_CATALOG_TITLE_LINK:
			    if (qName.equals("link")) {
			        mState = CATALOG_TITLES_CATALOG_TITLE;
					catalogTitle.link.add(link);
			    }
			    break;

            default:
                // do nothing
                break;
        }
    }

    @Override
    public void parse(final Object content) {
        // TODO Auto-generated method stub
    }
}

