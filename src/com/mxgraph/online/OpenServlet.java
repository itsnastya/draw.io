/**

+

*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
*
* $Id: OpenServlet.java,v 1.12 2013/10/16 12:31:25 david Exp $
 * Copyright (c) 2011-2012, JGraph Ltd
 */
package com.mxgraph.online;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class OpenServlet
 */
public class OpenServlet extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Global switch to enabled VDX support.
	 */
	public static boolean ENABLE_VDX_SUPPORT = true;

	/**
	 * Global switch to enabled VSDX support.
	 */
	public static boolean ENABLE_VSDX_SUPPORT = true;

	/**
	 * Global switch to enabled Gliffy support.
	 */
	public static boolean ENABLE_GLIFFY_SUPPORT = true;

	/**
	 * Global switch to enabled GraphML support.
	 */
	public static boolean ENABLE_GRAPHML_SUPPORT = true;

	/**
	 *
	 */
	public static final int PNG_CHUNK_ZTXT = 2052348020;

	/**
	 *
	 */
	public static final int PNG_CHUNK_IEND = 1229278788;

	/**
	 *
	 */
	protected static String gliffyRegex = "(?s).*\"contentType\":\\s*\"application/gliffy\\+json\".*";

	/**
	 *
	 */
	protected static String graphMlRegex = "(?s).*<graphml xmlns=\".*";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public OpenServlet()
	{
		super();
	}

    public String jsonArray(ArrayList<String> array) {
        String result = "[";
        Boolean flag = false;
        for (String str : array)
        {
            if (flag)
            {
                result += ",";
            }
            else
            {
                flag = true;
            }

            result += "\"" + str + "\"";
        }
        result += "]";
        return result;
    }

    // Функция получения списка файлов
    public ArrayList<String> listFilesForFolder(final File folder) {
        ArrayList<String> files = new ArrayList<>();
        for (final File fileEntry : folder.listFiles())
        {
            if (fileEntry.isDirectory())
            {
                listFilesForFolder(fileEntry);
            }
            else
            {
                files.add(fileEntry.getName());
            }
        }

        return files;
    }

    // Функция открытия файла
    public static String read(String fileName) throws FileNotFoundException {
        //Этот спец. объект для построения строки
        StringBuilder sb = new StringBuilder();

        File file = new File("C:/Users/Monster/draw-io-master/git/draw.io/WebContent/diagrams/" + fileName);

        try
        {
            //Объект для чтения файла в буфер
            BufferedReader in = new BufferedReader(new FileReader(file.getAbsoluteFile()));
            try
            {
                //В цикле построчно считываем файл
                String s;
                while ((s = in.readLine()) != null)
                {
                    sb.append(s);
                    sb.append("\n");
                }
            }
            finally
            {
                //Также не забываем закрыть файл
                in.close();
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }

        //Возвращаем полученный текст с файла
        return sb.toString();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        PrintWriter writer = response.getWriter();

        final File folder = new File("C:/Users/Monster/draw-io-master/git/draw.io/WebContent/diagrams/");
        ArrayList<String> files = listFilesForFolder(folder);

        String list = request.getParameter("list");
        if (list != null && list.equals("1"))
        {
            writer.println(jsonArray(files));
        } else {
            String filename = request.getParameter("filename");
            if (filename != null && files.contains(filename))
            {
                writer.println(read(filename));
            }
            else
            {
                writer.println("File not found");
            }
        }

        writer.flush();
        writer.close();
    }
    /*protected void doPost(HttpServletRequest request,
    		HttpServletResponse response) throws ServletException, IOException
    {
    	PrintWriter writer = response.getWriter();

    	try
    	{
    		if (request.getContentLength() < Constants.MAX_REQUEST_SIZE)
    		{
    			String filename = "";
    			String format = null;
    			String upfile = null;
    			boolean vdx = false;
    			boolean vsdx = false;

    			ServletFileUpload upload = new ServletFileUpload();
    			FileItemIterator iterator = upload.getItemIterator(request);

    			while (iterator.hasNext())
    			{
    				FileItemStream item = iterator.next();
    				String name = item.getFieldName();
    				InputStream stream = item.openStream();

    				if (item.isFormField() && name.equals("format"))
    				{
    					format = Streams.asString(stream);
    				}
    				else if (name.equals("upfile"))
    				{
    					filename = item.getName();
    					vdx = filename.toLowerCase().endsWith(".vdx");
    					vsdx = filename.toLowerCase().endsWith(".vsdx");

    					if (vsdx)
    					{
    						upfile = Streams.asString(stream, "ISO-8859-1");
    					}
    					else
    					{
    						upfile = Streams.asString(stream,
    								Utils.CHARSET_FOR_URL_ENCODING);
    					}
    				}
    			}

    			if (format == null)
    			{
    				format = request.getParameter("format");
    			}

    			if (format == null)
    			{
    				format = "html";
    			}

    			String xml = null;

    			if (filename.toLowerCase().endsWith(".png"))
    			{
    				xml = extractXmlFromPng(upfile
    						.getBytes(Utils.CHARSET_FOR_URL_ENCODING));
    			}
    			else if (ENABLE_GRAPHML_SUPPORT && upfile.matches(graphMlRegex))
    			{
    				// Creates a graph that contains a model but does not validate
    				// since that is not needed for the model and not allowed on GAE
    				mxGraph graph = new mxGraphHeadless()
    				{
    					@Override
                        public mxRectangle graphModelChanged(mxIGraphModel sender,
    							List<mxUndoableChange> changes)
    					{
    						return null;
    					}
    				};

    				mxGraphMlCodec.decode(mxXmlUtils.parseXml(upfile), graph);
    				xml = mxXmlUtils.getXml(new mxCodec().encode(graph.getModel()));
    			}
    			else if (ENABLE_VDX_SUPPORT && (vdx || vsdx))
    			{
    				mxGraph graph = new mxGraphHeadless()
    				{
    					@Override
                        public mxRectangle graphModelChanged(mxIGraphModel sender,
    							List<mxUndoableChange> changes)
    					{
    						return null;
    					}
    				};

    				graph.setConstrainChildren(false);
    				mxVsdxCodec vdxCodec = new mxVsdxCodec();

    				if (vdx)
    				{
    					Document doc = mxXmlUtils.parseXml(upfile);
    					//vdxCodec.decode(doc, graph);
    				}
    				else if (vsdx)
    				{
    					vdxCodec.decodeVsdx(
    							upfile.getBytes("ISO-8859-1"),
    							graph, Utils.CHARSET_FOR_URL_ENCODING);
    				}

    				mxCodec codec = new mxCodec();
    				Node node = codec.encode(graph.getModel());
    				// Specifies new stylesheet to be used
    				((Element) node).setAttribute("style", "default-style2");
    				xml = mxXmlUtils.getXml(node);

    				// Replaces VDX extension
    				int dot = filename.lastIndexOf('.');
    				filename = filename.substring(0, dot + 1) + "xml";
    			}
    			else if (ENABLE_GLIFFY_SUPPORT && upfile.matches(gliffyRegex))
    			{
    				GliffyDiagramConverter converter = new GliffyDiagramConverter(
    						upfile);
    				xml = converter.getGraphXml();
    			}

    			// Fallback to old data parameter
    			if (xml == null)
    			{
    				xml = (upfile == null) ? request.getParameter("data")
    						: upfile;
    			}

    			if (!format.equals("xml"))
    			{
    				if (xml == null || xml.length() == 0)
    				{
    					writeScript(writer,
    							"window.parent.showOpenAlert({message:window.parent.mxResources.get('invalidOrMissingFile')});");
    				}
    				else
    				{
    					// Workaround for replacement char and null byte in IE9 request
    					xml = xml.replaceAll("[\\uFFFD\\u0000]*", "");
    					writeScript(writer, "try{window.parent.setCurrentXml(decodeURIComponent('"
    						+ Utils.encodeURIComponent(xml, Utils.CHARSET_FOR_URL_ENCODING)
    						+ "'), decodeURIComponent('" + Utils.encodeURIComponent(filename, Utils.CHARSET_FOR_URL_ENCODING)
    						+ "'));}catch(e){window.parent.showOpenAlert({message:window.parent.mxResources.get('notAUtf8File')});}");
    				}
    			}
    			else
    			{
    				writer.println(xml);
    			}
    		}
    		else
    		{
    			response.setStatus(HttpServletResponse.SC_REQUEST_ENTITY_TOO_LARGE);
    			writeScript(
    					writer,
    					"window.parent.showOpenAlert(window.parent.mxResources.get('drawingTooLarge'));");
    		}
    	}
    	catch (Exception e)
    	{
    		e.printStackTrace();
    		response.setStatus(HttpServletResponse.SC_NOT_FOUND);
    		writeScript(
    				writer,
    				"window.parent.showOpenAlert(window.parent.mxResources.get('invalidOrMissingFile'));");
    	}

    	writer.flush();
    	writer.close();
    }*/

	/**
	 * Writes the given string as a script in a HTML page to the given print writer.
	 */
	protected void writeScript(PrintWriter writer, String js)
	{
		writer.println("<html>");
		writer.println("<body>");
		writer.println("<script type=\"text/javascript\">");
		writer.println(js);
		writer.println("</script>");
		writer.println("</body>");
		writer.println("</html>");
	}

	// NOTE: Key length must not be longer than 79 bytes (not checked)
	protected String extractXmlFromPng(byte[] data)
	{
		Map<String, String> textChunks = decodeCompressedText(new ByteArrayInputStream(
				data));

		return (textChunks != null) ? textChunks.get("mxGraphModel") : null;
	}

	/**
	 * Decodes the zTXt chunk of the given PNG image stream.
	 */
	public static Map<String, String> decodeCompressedText(InputStream stream)
	{
		Map<String, String> result = new Hashtable<>();

		if (!stream.markSupported())
		{
			stream = new BufferedInputStream(stream);
		}
		DataInputStream distream = new DataInputStream(stream);

		try
		{
			long magic = distream.readLong();

			if (magic != 0x89504e470d0a1a0aL)
			{
				throw new RuntimeException("PNGImageDecoder0");
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw new RuntimeException("PNGImageDecoder1");
		}

		try
		{
			while (distream.available() > 0)
			{
				int length = distream.readInt();
				int type = distream.readInt();
				byte[] data = new byte[length];
				distream.readFully(data);
				distream.readInt(); // Move past the crc

				if (type == PNG_CHUNK_IEND)
				{
					return null;
				}
				else if (type == PNG_CHUNK_ZTXT)
				{
					int currentIndex = 0;
					while ((data[currentIndex++]) != 0)
					{
					}

					String key = new String(data, 0, currentIndex - 1);

					try
					{
						byte[] bytes = Arrays.copyOfRange(data,
								currentIndex + 1, data.length);
						String value = URLDecoder.decode(Utils.inflate(bytes),
								Utils.CHARSET_FOR_URL_ENCODING);
						result.put(key, value);
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

					// No need to parse the rest of the PNG
					return result;
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
	}
}
